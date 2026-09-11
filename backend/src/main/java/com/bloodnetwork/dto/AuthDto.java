package com.bloodnetwork.dto;

import java.time.LocalDate;

import com.bloodnetwork.entity.enums.RoleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        public LoginRequest() {}
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String fullName;
        private String email;
        private RoleType role;
        private String city;
        private Boolean verified;

        public LoginResponse() {}
        public LoginResponse(String token, String type, Long id, String fullName, String email, RoleType role, String city, Boolean verified) {
            this.token = token;
            this.type = type != null ? type : "Bearer";
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
            this.city = city;
            this.verified = verified;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public RoleType getRole() { return role; }
        public void setRole(RoleType role) { this.role = role; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public Boolean getVerified() { return verified; }
        public void setVerified(Boolean verified) { this.verified = verified; }

        public static LoginResponseBuilder builder() { return new LoginResponseBuilder(); }
        public static class LoginResponseBuilder {
            private String token;
            private String type = "Bearer";
            private Long id;
            private String fullName;
            private String email;
            private RoleType role;
            private String city;
            private Boolean verified;

            public LoginResponseBuilder token(String token) { this.token = token; return this; }
            public LoginResponseBuilder type(String type) { this.type = type; return this; }
            public LoginResponseBuilder id(Long id) { this.id = id; return this; }
            public LoginResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public LoginResponseBuilder email(String email) { this.email = email; return this; }
            public LoginResponseBuilder role(RoleType role) { this.role = role; return this; }
            public LoginResponseBuilder city(String city) { this.city = city; return this; }
            public LoginResponseBuilder verified(Boolean verified) { this.verified = verified; return this; }

            public LoginResponse build() {
                return new LoginResponse(token, type, id, fullName, email, role, city, verified);
            }
        }
    }

    public static class DonorRegisterRequest {
        @NotBlank(message = "Full name is required")
        private String fullName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String city;
        private String address;

        @NotBlank(message = "Blood group is required")
        private String bloodGroup;

        private LocalDate dateOfBirth;
        private String gender;
        private Boolean isAvailable = true;

        public DonorRegisterRequest() {}

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
        public LocalDate getDateOfBirth() { return dateOfBirth; }
        public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public Boolean getIsAvailable() { return isAvailable; }
        public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
    }

    public static class PatientRegisterRequest {
        @NotBlank(message = "Full name is required")
        private String fullName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String city;
        private String address;
        private String bloodGroup;
        private String emergencyContactName;
        private String emergencyContactPhone;

        public PatientRegisterRequest() {}

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
        public String getEmergencyContactName() { return emergencyContactName; }
        public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
        public String getEmergencyContactPhone() { return emergencyContactPhone; }
        public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    }

    public static class HospitalRegisterRequest {
        @NotBlank(message = "Hospital name is required")
        private String hospitalName;

        @NotBlank(message = "License number is required")
        private String licenseNumber;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String city;
        private String address;

        @NotBlank(message = "Emergency contact number is required")
        private String emergencyContactNumber;

        private Integer bedCount;
        private String department;

        public HospitalRegisterRequest() {}

        public String getHospitalName() { return hospitalName; }
        public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }
        public String getLicenseNumber() { return licenseNumber; }
        public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getEmergencyContactNumber() { return emergencyContactNumber; }
        public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }
        public Integer getBedCount() { return bedCount; }
        public void setBedCount(Integer bedCount) { this.bedCount = bedCount; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }

    public static class BloodBankRegisterRequest {
        @NotBlank(message = "Blood bank name is required")
        private String bloodBankName;

        @NotBlank(message = "Registration number is required")
        private String registrationNumber;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String city;
        private String address;

        @NotBlank(message = "Contact person is required")
        private String contactPerson;

        private String operatingHours;

        public BloodBankRegisterRequest() {}

        public String getBloodBankName() { return bloodBankName; }
        public void setBloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; }
        public String getRegistrationNumber() { return registrationNumber; }
        public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getContactPerson() { return contactPerson; }
        public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
        public String getOperatingHours() { return operatingHours; }
        public void setOperatingHours(String operatingHours) { this.operatingHours = operatingHours; }
    }

    public static class UserProfileDto {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private RoleType role;
        private String city;
        private String address;
        private Boolean verified;

        public UserProfileDto() {}
        public UserProfileDto(Long id, String fullName, String email, String phone, RoleType role, String city, String address, Boolean verified) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.city = city;
            this.address = address;
            this.verified = verified;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public RoleType getRole() { return role; }
        public void setRole(RoleType role) { this.role = role; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public Boolean getVerified() { return verified; }
        public void setVerified(Boolean verified) { this.verified = verified; }

        public static UserProfileDtoBuilder builder() { return new UserProfileDtoBuilder(); }
        public static class UserProfileDtoBuilder {
            private Long id;
            private String fullName;
            private String email;
            private String phone;
            private RoleType role;
            private String city;
            private String address;
            private Boolean verified;

            public UserProfileDtoBuilder id(Long id) { this.id = id; return this; }
            public UserProfileDtoBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public UserProfileDtoBuilder email(String email) { this.email = email; return this; }
            public UserProfileDtoBuilder phone(String phone) { this.phone = phone; return this; }
            public UserProfileDtoBuilder role(RoleType role) { this.role = role; return this; }
            public UserProfileDtoBuilder city(String city) { this.city = city; return this; }
            public UserProfileDtoBuilder address(String address) { this.address = address; return this; }
            public UserProfileDtoBuilder verified(Boolean verified) { this.verified = verified; return this; }

            public UserProfileDto build() {
                return new UserProfileDto(id, fullName, email, phone, role, city, address, verified);
            }
        }
    }
}
