package com.bloodnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.Hospital;
import com.bloodnetwork.entity.User;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByUser(User user);

    Optional<Hospital> findByUserId(Long userId);

    Optional<Hospital> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    @Query("SELECT h FROM Hospital h WHERE (:city IS NULL OR LOWER(h.user.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    List<Hospital> searchHospitals(@Param("city") String city);
}
