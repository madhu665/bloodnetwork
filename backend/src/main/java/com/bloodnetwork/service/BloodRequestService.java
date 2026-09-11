package com.bloodnetwork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.BloodRequestDto;
import com.bloodnetwork.entity.BloodRequest;
import com.bloodnetwork.entity.DonorProfile;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.NotificationType;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.UrgencyLevel;
import com.bloodnetwork.exception.ResourceNotFoundException;
import com.bloodnetwork.repository.BloodRequestRepository;
import com.bloodnetwork.repository.DonorProfileRepository;
import com.bloodnetwork.util.BloodCompatibilityUtil;

@Service
public class BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private DonorProfileRepository donorProfileRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public BloodRequestDto.Response createRequest(BloodRequestDto.CreateRequest req, User requester) {
        BloodRequest bloodRequest = BloodRequest.builder()
                .requester(requester)
                .patientName(req.getPatientName())
                .bloodGroup(req.getBloodGroup())
                .unitsRequired(req.getUnitsRequired())
                .urgency(req.getUrgency())
                .status(RequestStatus.PENDING)
                .hospitalName(req.getHospitalName())
                .hospitalAddress(req.getHospitalAddress())
                .city(req.getCity())
                .requiredDate(req.getRequiredDate())
                .contactPhone(req.getContactPhone())
                .additionalNotes(req.getAdditionalNotes())
                .matchedDonorCount(0)
                .build();

        bloodRequest = bloodRequestRepository.save(bloodRequest);

        // Medical Compatibility & Matching Algorithm:
        // Find donor blood groups compatible with the recipient patient
        List<BloodGroup> compatibleGroups = BloodCompatibilityUtil.getCompatibleDonorGroups(req.getBloodGroup());

        // Find available donors in the requested city matching compatible blood types
        List<DonorProfile> matchedDonors = donorProfileRepository.findCompatibleAvailableInCity(
                compatibleGroups,
                req.getCity()
        );

        // Filter for donors who are currently eligible (> 90 days cooldown)
        List<DonorProfile> eligibleDonors = matchedDonors.stream()
                .filter(DonorProfile::isEligibleToDonate)
                .collect(Collectors.toList());

        bloodRequest.setMatchedDonorCount(eligibleDonors.size());
        if (!eligibleDonors.isEmpty()) {
            bloodRequest.setStatus(RequestStatus.MATCHING);
        }
        bloodRequest = bloodRequestRepository.save(bloodRequest);

        // Dispatch instant in-app alerts to eligible compatible donors
        String urgencyTag = req.getUrgency() == UrgencyLevel.CRITICAL ? "CRITICAL EMERGENCY" : "URGENT";
        for (DonorProfile donor : eligibleDonors) {
            String title = String.format("[%s] Blood Request: %s in %s", urgencyTag, req.getBloodGroup().getDisplayValue(), req.getCity());
            String message = String.format(
                    "Patient %s at %s requires %d units of %s blood by %s. Your blood type (%s) is compatible. Please respond if you can donate.",
                    req.getPatientName(),
                    req.getHospitalName(),
                    req.getUnitsRequired(),
                    req.getBloodGroup().getDisplayValue(),
                    req.getRequiredDate(),
                    donor.getBloodGroup().getDisplayValue()
            );

            notificationService.createNotification(
                    donor.getUser(),
                    title,
                    message,
                    NotificationType.EMERGENCY_REQUEST,
                    bloodRequest.getId()
            );
        }

        return mapToDto(bloodRequest);
    }

    public List<BloodRequestDto.Response> filterRequests(
            RequestStatus status,
            UrgencyLevel urgency,
            BloodGroup bloodGroup,
            String city) {

        return bloodRequestRepository.filterRequests(status, urgency, bloodGroup, city)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BloodRequestDto.Response getRequestById(Long id) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with ID: " + id));
        return mapToDto(req);
    }

    @Transactional
    public BloodRequestDto.Response updateStatus(Long id, RequestStatus newStatus, User updater) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with ID: " + id));

        req.setStatus(newStatus);
        req = bloodRequestRepository.save(req);

        // Notify requester of status change
        notificationService.createNotification(
                req.getRequester(),
                "Blood Request Update: " + newStatus.name().replace("_", " "),
                "Your blood request #" + req.getId() + " for patient " + req.getPatientName() + " has been updated to " + newStatus.name(),
                NotificationType.STATUS_UPDATE,
                req.getId()
        );

        return mapToDto(req);
    }

    @Transactional
    public BloodRequestDto.Response acceptRequest(Long id, User donor) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with ID: " + id));

        if (req.getStatus() != RequestStatus.MATCHING && req.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Request is no longer available.");
        }

        req.setStatus(RequestStatus.DONOR_FOUND);
        req.setAcceptedDonor(donor);
        req = bloodRequestRepository.save(req);

        // Notify requester (Hospital/Patient) that a donor accepted
        notificationService.createNotification(
                req.getRequester(),
                "Donor Found!",
                donor.getFullName() + " has accepted your urgent blood request for patient " + req.getPatientName(),
                NotificationType.STATUS_UPDATE,
                req.getId()
        );

        return mapToDto(req);
    }

    public List<BloodRequestDto.Response> getMyRequests(User user) {
        return bloodRequestRepository.findByRequesterIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Object getLiveTracking(Long id) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with ID: " + id));

        if (req.getAcceptedDonor() == null) {
            throw new RuntimeException("No donor has accepted this request yet.");
        }

        DonorProfile profile = donorProfileRepository.findByUser(req.getAcceptedDonor())
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found"));

        return new java.util.HashMap<String, Object>() {{
            put("latitude", profile.getLatitude());
            put("longitude", profile.getLongitude());
            put("lastUpdate", profile.getLastLocationUpdate());
            put("donorName", req.getAcceptedDonor().getFullName());
            put("contactPhone", req.getAcceptedDonor().getPhone());
        }};
    }

    private BloodRequestDto.Response mapToDto(BloodRequest r) {
        return BloodRequestDto.Response.builder()
                .id(r.getId())
                .requesterId(r.getRequester().getId())
                .requesterName(r.getRequester().getFullName())
                .patientName(r.getPatientName())
                .bloodGroup(r.getBloodGroup())
                .unitsRequired(r.getUnitsRequired())
                .urgency(r.getUrgency())
                .status(r.getStatus())
                .hospitalName(r.getHospitalName())
                .hospitalAddress(r.getHospitalAddress())
                .city(r.getCity())
                .requiredDate(r.getRequiredDate())
                .contactPhone(r.getContactPhone())
                .additionalNotes(r.getAdditionalNotes())
                .matchedDonorCount(r.getMatchedDonorCount())
                .acceptedDonorId(r.getAcceptedDonor() != null ? r.getAcceptedDonor().getId() : null)
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
}
