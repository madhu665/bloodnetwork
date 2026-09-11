package com.bloodnetwork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.PatientProfile;
import com.bloodnetwork.entity.User;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

    Optional<PatientProfile> findByUser(User user);

    Optional<PatientProfile> findByUserId(Long userId);
}
