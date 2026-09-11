package com.bloodnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.DonorProfile;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;

@Repository
public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long> {

    Optional<DonorProfile> findByUser(User user);

    Optional<DonorProfile> findByUserId(Long userId);

    List<DonorProfile> findByBloodGroupAndIsAvailableTrue(BloodGroup bloodGroup);

    List<DonorProfile> findByBloodGroupInAndIsAvailableTrue(List<BloodGroup> bloodGroups);

    @Query("SELECT d FROM DonorProfile d WHERE d.isAvailable = true " +
           "AND (:bloodGroup IS NULL OR d.bloodGroup = :bloodGroup) " +
           "AND (:city IS NULL OR LOWER(d.user.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    List<DonorProfile> searchDonors(@Param("bloodGroup") BloodGroup bloodGroup, @Param("city") String city);

    @Query("SELECT d FROM DonorProfile d WHERE d.isAvailable = true " +
           "AND d.bloodGroup IN :bloodGroups " +
           "AND LOWER(d.user.city) = LOWER(:city)")
    List<DonorProfile> findCompatibleAvailableInCity(
        @Param("bloodGroups") List<BloodGroup> bloodGroups,
        @Param("city") String city
    );
}
