package com.bloodnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.BloodRequest;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.UrgencyLevel;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    List<BloodRequest> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);

    List<BloodRequest> findByStatusOrderByCreatedAtDesc(RequestStatus status);

    List<BloodRequest> findByUrgencyOrderByCreatedAtDesc(UrgencyLevel urgency);

    long countByStatus(RequestStatus status);

    long countByUrgency(UrgencyLevel urgency);

    @Query("SELECT r FROM BloodRequest r WHERE " +
           "(:status IS NULL OR r.status = :status) AND " +
           "(:urgency IS NULL OR r.urgency = :urgency) AND " +
           "(:bloodGroup IS NULL OR r.bloodGroup = :bloodGroup) AND " +
           "(:city IS NULL OR LOWER(r.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "ORDER BY " +
           "CASE WHEN r.urgency = 'CRITICAL' THEN 1 WHEN r.urgency = 'URGENT' THEN 2 ELSE 3 END, " +
           "r.createdAt DESC")
    List<BloodRequest> filterRequests(
        @Param("status") RequestStatus status,
        @Param("urgency") UrgencyLevel urgency,
        @Param("bloodGroup") BloodGroup bloodGroup,
        @Param("city") String city
    );
}
