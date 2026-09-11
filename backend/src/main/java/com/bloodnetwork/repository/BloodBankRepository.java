package com.bloodnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.entity.User;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {

    Optional<BloodBank> findByUser(User user);

    Optional<BloodBank> findByUserId(Long userId);

    Optional<BloodBank> findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    @Query("SELECT b FROM BloodBank b WHERE (:city IS NULL OR LOWER(b.user.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    List<BloodBank> searchBloodBanks(@Param("city") String city);
}
