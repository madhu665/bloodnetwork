package com.bloodnetwork.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.entity.BloodInventory;
import com.bloodnetwork.entity.BloodRequest;
import com.bloodnetwork.entity.DonorProfile;
import com.bloodnetwork.entity.Hospital;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.RoleType;
import com.bloodnetwork.entity.enums.UrgencyLevel;
import com.bloodnetwork.repository.BloodBankRepository;
import com.bloodnetwork.repository.BloodInventoryRepository;
import com.bloodnetwork.repository.BloodRequestRepository;
import com.bloodnetwork.repository.DonorProfileRepository;
import com.bloodnetwork.repository.HospitalRepository;
import com.bloodnetwork.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonorProfileRepository donorProfileRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Data already exists
        }

        System.out.println("Initializing Blood Response Network seed data...");

        // 1. ADMIN USER
        User admin = User.builder()
                .fullName("System Administrator")
                .email("admin@bloodnetwork.org")
                .password(passwordEncoder.encode("Admin@123"))
                .phone("+91 98765 43210")
                .role(RoleType.ROLE_ADMIN)
                .city("Hyderabad")
                .address("Health City, Cyber Towers Road")
                .verified(true)
                .active(true)
                .build();
        userRepository.save(admin);

        // 2. DONORS
        User donor1 = createDonor("Dr. Rajesh Sharma", "rajesh.donor@example.com", "Donor@123", "+91 91234 56780", "Hyderabad", BloodGroup.O_NEG, 12, 17.4123, 78.4412);
        User donor2 = createDonor("Sneha Reddy", "sneha.donor@example.com", "Donor@123", "+91 91234 56781", "Hyderabad", BloodGroup.A_POS, 6, 17.4321, 78.4321);
        createDonor("Vikram Verma", "vikram.donor@example.com", "Donor@123", "+91 91234 56782", "Hyderabad", BloodGroup.B_POS, 4, 17.4567, 78.4567);
        createDonor("Priya Nair", "priya.donor@example.com", "Donor@123", "+91 91234 56783", "Hyderabad", BloodGroup.AB_POS, 8, 17.4789, 78.4789);
        createDonor("Arjun Patel", "arjun.donor@example.com", "Donor@123", "+91 91234 56784", "Mumbai", BloodGroup.O_POS, 15, 19.0760, 72.8777);

        // 3. HOSPITALS
        User hospUser1 = User.builder()
                .fullName("Apollo Hospitals Jubilee Hills")
                .email("apollo@hospitals.org")
                .password(passwordEncoder.encode("Hospital@123"))
                .phone("+91 40 2360 7777")
                .role(RoleType.ROLE_HOSPITAL)
                .city("Hyderabad")
                .address("Road No. 72, Film Nagar, Jubilee Hills")
                .verified(true)
                .active(true)
                .build();
        userRepository.save(hospUser1);

        Hospital hosp1 = Hospital.builder()
                .user(hospUser1)
                .hospitalName("Apollo Hospitals Jubilee Hills")
                .licenseNumber("HOSP-HYD-2024-001")
                .emergencyContactNumber("+91 40 2360 8888")
                .department("Trauma & Emergency Care")
                .bedCount(450)
                .build();
        hospitalRepository.save(hosp1);

        // 4. BLOOD BANKS
        User bankUser1 = User.builder()
                .fullName("Red Cross Central Blood Bank")
                .email("redcross@bloodbank.org")
                .password(passwordEncoder.encode("Bank@123"))
                .phone("+91 40 2763 3333")
                .role(RoleType.ROLE_BLOOD_BANK)
                .city("Hyderabad")
                .address("Red Cross Road, Narayanguda")
                .verified(true)
                .active(true)
                .build();
        userRepository.save(bankUser1);

        BloodBank bank1 = BloodBank.builder()
                .user(bankUser1)
                .bloodBankName("Red Cross Central Blood Bank")
                .registrationNumber("BB-TG-HYD-1092")
                .contactPerson("Dr. Meera Sundaram")
                .operatingHours("24 Hours / 7 Days")
                .build();
        bank1 = bloodBankRepository.save(bank1);

        // Populate stock for all 8 blood groups
        int[] units = {18, 5, 24, 8, 12, 3, 30, 9};
        BloodGroup[] groups = BloodGroup.values();
        for (int i = 0; i < groups.length; i++) {
            BloodInventory inv = BloodInventory.builder()
                    .bloodBank(bank1)
                    .bloodGroup(groups[i])
                    .availableUnits(units[i])
                    .reservedUnits(2)
                    .criticalThresholdUnits(6)
                    .lastRestockedAt(LocalDateTime.now().minusDays(1))
                    .build();
            bloodInventoryRepository.save(inv);
        }

        // 5. SAMPLE BLOOD REQUESTS
        BloodRequest req1 = BloodRequest.builder()
                .requester(hospUser1)
                .patientName("Sunil Kumar")
                .bloodGroup(BloodGroup.O_NEG)
                .unitsRequired(3)
                .urgency(UrgencyLevel.CRITICAL)
                .status(RequestStatus.MATCHING)
                .hospitalName("Apollo Hospitals Jubilee Hills")
                .hospitalAddress("Emergency Ward, 2nd Floor")
                .city("Hyderabad")
                .requiredDate(LocalDate.now().plusDays(1))
                .contactPhone("+91 98490 11223")
                .additionalNotes("Immediate open heart surgery requirement. Compatible O- units urgently requested.")
                .matchedDonorCount(2)
                .build();
        bloodRequestRepository.save(req1);

        BloodRequest req2 = BloodRequest.builder()
                .requester(hospUser1)
                .patientName("Ananya Sen")
                .bloodGroup(BloodGroup.A_POS)
                .unitsRequired(2)
                .urgency(UrgencyLevel.URGENT)
                .status(RequestStatus.DONOR_FOUND)
                .acceptedDonor(donor2)
                .hospitalName("Care Hospitals Banjara Hills")
                .hospitalAddress("Road No. 1, Banjara Hills")
                .city("Hyderabad")
                .requiredDate(LocalDate.now().plusDays(2))
                .contactPhone("+91 98490 44556")
                .additionalNotes("Scheduled chemotherapy transfusion. Donor Sneha Reddy en route.")
                .matchedDonorCount(3)
                .build();
        bloodRequestRepository.save(req2);

        System.out.println("Seed data initialized successfully!");
    }

    private User createDonor(String name, String email, String pass, String phone, String city, BloodGroup bg, int donations, Double lat, Double lng) {
        User user = User.builder()
                .fullName(name)
                .email(email)
                .password(passwordEncoder.encode(pass))
                .phone(phone)
                .role(RoleType.ROLE_DONOR)
                .city(city)
                .address("Residential Sector, " + city)
                .verified(true)
                .active(true)
                .build();
        user = userRepository.save(user);

        DonorProfile donor = DonorProfile.builder()
                .user(user)
                .bloodGroup(bg)
                .dateOfBirth(LocalDate.of(1995, 5, 15))
                .gender("Not Specified")
                .isAvailable(true)
                .lastDonationDate(LocalDate.now().minusMonths(4))
                .totalDonations(donations)
                .emergencyDonor(true)
                .latitude(lat)
                .longitude(lng)
                .lastLocationUpdate(LocalDateTime.now())
                .build();
        donorProfileRepository.save(donor);
        return user;
    }
}
