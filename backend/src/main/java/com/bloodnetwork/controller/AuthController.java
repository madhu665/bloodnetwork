package com.bloodnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodnetwork.dto.AuthDto;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.security.CustomUserDetails;
import com.bloodnetwork.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register/donor")
    public ResponseEntity<AuthDto.LoginResponse> registerDonor(@Valid @RequestBody AuthDto.DonorRegisterRequest request) {
        return ResponseEntity.ok(authService.registerDonor(request));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<AuthDto.LoginResponse> registerPatient(@Valid @RequestBody AuthDto.PatientRegisterRequest request) {
        return ResponseEntity.ok(authService.registerPatient(request));
    }

    @PostMapping("/register/hospital")
    public ResponseEntity<AuthDto.LoginResponse> registerHospital(@Valid @RequestBody AuthDto.HospitalRegisterRequest request) {
        return ResponseEntity.ok(authService.registerHospital(request));
    }

    @PostMapping("/register/blood-bank")
    public ResponseEntity<AuthDto.LoginResponse> registerBloodBank(@Valid @RequestBody AuthDto.BloodBankRegisterRequest request) {
        return ResponseEntity.ok(authService.registerBloodBank(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthDto.UserProfileDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(AuthDto.UserProfileDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .city(user.getCity())
                .address(user.getAddress())
                .verified(user.getVerified())
                .build());
    }
}
