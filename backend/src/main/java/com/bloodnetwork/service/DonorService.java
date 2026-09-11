package com.bloodnetwork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.DashboardStatsDto;
import com.bloodnetwork.dto.DonorDto;
import com.bloodnetwork.entity.Donation;
import com.bloodnetwork.entity.DonorProfile;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.exception.ResourceNotFoundException;
import com.bloodnetwork.repository.DonationRepository;
import com.bloodnetwork.repository.DonorProfileRepository;
import com.bloodnetwork.repository.NotificationRepository;

@Service
public class DonorService {

    @Autowired
    private DonorProfileRepository donorProfileRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public List<DonorDto.PublicDonorResponse> searchDonors(BloodGroup bloodGroup, String city) {
        List<DonorProfile> donors = donorProfileRepository.searchDonors(bloodGroup, city);
        return donors.stream().map(d -> DonorDto.PublicDonorResponse.builder()
                .id(d.getId())
                .fullName(d.getUser().getFullName())
                .bloodGroup(d.getBloodGroup())
                .city(d.getUser().getCity())
                .isAvailable(d.getIsAvailable())
                .totalDonations(d.getTotalDonations())
                .eligible(d.isEligibleToDonate())
                .nextEligibleDate(d.getNextEligibleDate())
                .build()
        ).collect(Collectors.toList());
    }

    public DonorDto.DetailedDonorResponse getDonorProfile(User user) {
        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + user.getEmail()));

        return mapToDetailedDto(profile);
    }

    @Transactional
    public DonorDto.DetailedDonorResponse updateAvailability(User user, boolean isAvailable) {
        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + user.getEmail()));

        profile.setIsAvailable(isAvailable);
        profile = donorProfileRepository.save(profile);
        return mapToDetailedDto(profile);
    }

    public DashboardStatsDto.DonorStats getDonorDashboardStats(User user) {
        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + user.getEmail()));

        long unread = notificationRepository.countByRecipientIdAndIsReadFalse(user.getId());
        int donations = profile.getTotalDonations() != null ? profile.getTotalDonations() : 0;
        int livesSaved = donations * 3; // 1 blood donation can save up to 3 lives

        return DashboardStatsDto.DonorStats.builder()
                .bloodGroup(profile.getBloodGroup().getDisplayValue())
                .eligible(profile.isEligibleToDonate())
                .lastDonationDate(profile.getLastDonationDate() != null ? profile.getLastDonationDate().toString() : "No record")
                .nextEligibleDate(profile.getNextEligibleDate().toString())
                .totalDonations(donations)
                .livesSaved(livesSaved)
                .isAvailable(Boolean.TRUE.equals(profile.getIsAvailable()))
                .unreadNotifications(unread)
                .build();
    }

    public List<Donation> getDonationHistory(User user) {
        return donationRepository.findByDonorOrderByDonationDateDesc(user);
    }

    @Transactional
    public void updateLocation(User user, Double latitude, Double longitude) {
        DonorProfile profile = donorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Donor profile not found for user: " + user.getEmail()));
        
        profile.setLatitude(latitude);
        profile.setLongitude(longitude);
        profile.setLastLocationUpdate(java.time.LocalDateTime.now());
        donorProfileRepository.save(profile);
    }

    private DonorDto.DetailedDonorResponse mapToDetailedDto(DonorProfile d) {
        return DonorDto.DetailedDonorResponse.builder()
                .id(d.getId())
                .userId(d.getUser().getId())
                .fullName(d.getUser().getFullName())
                .email(d.getUser().getEmail())
                .phone(d.getUser().getPhone())
                .city(d.getUser().getCity())
                .address(d.getUser().getAddress())
                .bloodGroup(d.getBloodGroup())
                .dateOfBirth(d.getDateOfBirth())
                .gender(d.getGender())
                .isAvailable(d.getIsAvailable())
                .lastDonationDate(d.getLastDonationDate())
                .totalDonations(d.getTotalDonations())
                .emergencyDonor(d.getEmergencyDonor())
                .eligible(d.isEligibleToDonate())
                .nextEligibleDate(d.getNextEligibleDate())
                .latitude(d.getLatitude())
                .longitude(d.getLongitude())
                .build();
    }
}
