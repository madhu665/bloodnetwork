package com.bloodnetwork.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.DashboardStatsDto;
import com.bloodnetwork.entity.AuditLog;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.RoleType;
import com.bloodnetwork.entity.enums.UrgencyLevel;
import com.bloodnetwork.exception.ResourceNotFoundException;
import com.bloodnetwork.repository.AuditLogRepository;
import com.bloodnetwork.repository.BloodInventoryRepository;
import com.bloodnetwork.repository.BloodRequestRepository;
import com.bloodnetwork.repository.DonationRepository;
import com.bloodnetwork.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    public DashboardStatsDto.AdminStats getAdminStats() {
        long totalUsers = userRepository.count();
        long totalDonors = userRepository.countByRole(RoleType.ROLE_DONOR);
        long totalHospitals = userRepository.countByRole(RoleType.ROLE_HOSPITAL);
        long totalBloodBanks = userRepository.countByRole(RoleType.ROLE_BLOOD_BANK);

        long activeRequests = bloodRequestRepository.countByStatus(RequestStatus.PENDING) +
                              bloodRequestRepository.countByStatus(RequestStatus.MATCHING) +
                              bloodRequestRepository.countByStatus(RequestStatus.IN_PROGRESS);

        long emergencyRequests = bloodRequestRepository.countByUrgency(UrgencyLevel.CRITICAL) +
                                 bloodRequestRepository.countByUrgency(UrgencyLevel.URGENT);

        Long totalUnits = bloodInventoryRepository.getTotalAvailableUnits();
        long totalUnitsAvailable = totalUnits != null ? totalUnits : 0;

        Long totalDonationUnits = donationRepository.sumTotalUnitsDonated();
        long totalDonations = totalDonationUnits != null ? totalDonationUnits : 0;

        long fulfilledRequests = bloodRequestRepository.countByStatus(RequestStatus.FULFILLED);

        Map<String, Long> groupInventory = new HashMap<>();
        for (BloodGroup bg : BloodGroup.values()) {
            Long count = bloodInventoryRepository.getTotalAvailableUnitsByBloodGroup(bg);
            groupInventory.put(bg.getDisplayValue(), count != null ? count : 0L);
        }

        return DashboardStatsDto.AdminStats.builder()
                .totalUsers(totalUsers)
                .totalDonors(totalDonors)
                .totalHospitals(totalHospitals)
                .totalBloodBanks(totalBloodBanks)
                .activeRequests(activeRequests)
                .emergencyRequests(emergencyRequests)
                .totalUnitsAvailable(totalUnitsAvailable)
                .totalDonations(totalDonations)
                .fulfilledRequests(fulfilledRequests)
                .bloodGroupInventory(groupInventory)
                .build();
    }

    public List<User> getAllUsers(RoleType role) {
        if (role != null) {
            return userRepository.findAllByRole(role);
        }
        return userRepository.findAll();
    }

    @Transactional
    public User verifyUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        user.setVerified(true);
        user = userRepository.save(user);

        logAction(userId, user.getEmail(), "VERIFY_USER", "User", userId, "User account successfully verified by Admin");
        return user;
    }

    @Transactional
    public User toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        boolean newActive = !Boolean.TRUE.equals(user.getActive());
        user.setActive(newActive);
        user = userRepository.save(user);

        String action = newActive ? "REACTIVATE_USER" : "SUSPEND_USER";
        logAction(userId, user.getEmail(), action, "User", userId, "User account status toggled to: " + (newActive ? "Active" : "Suspended"));
        return user;
    }

    public List<AuditLog> getRecentAuditLogs() {
        return auditLogRepository.findTop50ByOrderByTimestampDesc();
    }

    public void logAction(Long userId, String email, String action, String entityName, Long entityId, String details) {
        AuditLog log = AuditLog.builder()
                .userId(userId)
                .userEmail(email)
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .details(details)
                .build();
        auditLogRepository.save(log);
    }
}
