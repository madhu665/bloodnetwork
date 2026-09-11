package com.bloodnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.RoleType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRole(RoleType role);

    long countByRole(RoleType role);

    long countByActiveTrue();
}