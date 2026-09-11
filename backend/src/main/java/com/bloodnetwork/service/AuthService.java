package com.bloodnetwork.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.AuthDto;
import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.entity.BloodInventory;
import com.bloodnetwork.entity.DonorProfile;
import com.bloodnetwork.entity.Hospital;
import com.bloodnetwork.entity.PatientProfile;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RoleType;
import com.bloodnetwork.exception.BadRequestException;
import com.bloodnetwork.repository.BloodBankRepository;
import com.bloodnetwork.repository.BloodInventoryRepository;
import com.bloodnetwork.repository.DonorProfileRepository;
import com.bloodnetwork.repository.HospitalRepository;
import com.bloodnetwork.repository.PatientProfileRepository;
import com.bloodnetwork.repository.UserRepository;
import com.bloodnetwork.security.CustomUserDetails;
import com.bloodnetwork.security.JwtUtils;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonorProfileRepository donorProfileRepository;

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtils.generateToken(userDetails, user.getRole().name(), user.getId());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .city(user.getCity())
                .verified(user.getVerified())
                .build();
    }

    @Transactional
    public AuthDto.LoginResponse registerDonor(AuthDto.DonorRegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email is already registered: " + req.getEmail());
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(RoleType.ROLE_DONOR)
                .city(req.getCity())
                .address(req.getAddress())
                .verified(true)
                .active(true)
                .build();
        user = userRepository.save(user);

        BloodGroup bg = BloodGroup.fromString(req.getBloodGroup());
        DonorProfile profile = DonorProfile.builder()
                .user(user)
                .bloodGroup(bg)
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender())
                .isAvailable(req.getIsAvailable() != null ? req.getIsAvailable() : true)
                .totalDonations(0)
                .emergencyDonor(true)
                .build();
        donorProfileRepository.save(profile);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtils.generateToken(userDetails, user.getRole().name(), user.getId());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .city(user.getCity())
                .verified(user.getVerified())
                .build();
    }

    @Transactional
    public AuthDto.LoginResponse registerPatient(AuthDto.PatientRegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email is already registered: " + req.getEmail());
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(RoleType.ROLE_PATIENT)
                .city(req.getCity())
                .address(req.getAddress())
                .verified(true)
                .active(true)
                .build();
        user = userRepository.save(user);

        BloodGroup bg = req.getBloodGroup() != null ? BloodGroup.fromString(req.getBloodGroup()) : null;
        PatientProfile profile = PatientProfile.builder()
                .user(user)
                .bloodGroup(bg)
                .emergencyContactName(req.getEmergencyContactName())
                .emergencyContactPhone(req.getEmergencyContactPhone())
                .build();
        patientProfileRepository.save(profile);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtils.generateToken(userDetails, user.getRole().name(), user.getId());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .city(user.getCity())
                .verified(user.getVerified())
                .build();
    }

    @Transactional
    public AuthDto.LoginResponse registerHospital(AuthDto.HospitalRegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email is already registered: " + req.getEmail());
        }
        if (hospitalRepository.existsByLicenseNumber(req.getLicenseNumber())) {
            throw new BadRequestException("Hospital license number is already registered: " + req.getLicenseNumber());
        }

        User user = User.builder()
                .fullName(req.getHospitalName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(RoleType.ROLE_HOSPITAL)
                .city(req.getCity())
                .address(req.getAddress())
                .verified(false) // Requires admin verification
                .active(true)
                .build();
        user = userRepository.save(user);

        Hospital hospital = Hospital.builder()
                .user(user)
                .hospitalName(req.getHospitalName())
                .licenseNumber(req.getLicenseNumber())
                .emergencyContactNumber(req.getEmergencyContactNumber())
                .department(req.getDepartment())
                .bedCount(req.getBedCount())
                .build();
        hospitalRepository.save(hospital);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtils.generateToken(userDetails, user.getRole().name(), user.getId());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .city(user.getCity())
                .verified(user.getVerified())
                .build();
    }

    @Transactional
    public AuthDto.LoginResponse registerBloodBank(AuthDto.BloodBankRegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email is already registered: " + req.getEmail());
        }
        if (bloodBankRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
            throw new BadRequestException("Blood bank registration number is already registered: " + req.getRegistrationNumber());
        }

        User user = User.builder()
                .fullName(req.getBloodBankName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(RoleType.ROLE_BLOOD_BANK)
                .city(req.getCity())
                .address(req.getAddress())
                .verified(false) // Requires admin verification
                .active(true)
                .build();
        user = userRepository.save(user);

        BloodBank bloodBank = BloodBank.builder()
                .user(user)
                .bloodBankName(req.getBloodBankName())
                .registrationNumber(req.getRegistrationNumber())
                .contactPerson(req.getContactPerson())
                .operatingHours(req.getOperatingHours())
                .build();
        bloodBank = bloodBankRepository.save(bloodBank);

        // Auto-initialize 8 inventory records with 0 units
        for (BloodGroup bg : BloodGroup.values()) {
            BloodInventory inventory = BloodInventory.builder()
                    .bloodBank(bloodBank)
                    .bloodGroup(bg)
                    .availableUnits(0)
                    .reservedUnits(0)
                    .criticalThresholdUnits(5)
                    .build();
            bloodInventoryRepository.save(inventory);
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtUtils.generateToken(userDetails, user.getRole().name(), user.getId());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .city(user.getCity())
                .verified(user.getVerified())
                .build();
    }
}
