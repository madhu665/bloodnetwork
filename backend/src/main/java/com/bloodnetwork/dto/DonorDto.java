package com.bloodnetwork.dto;

import java.time.LocalDate;

import com.bloodnetwork.entity.enums.BloodGroup;

public class DonorDto {

    public static class PublicDonorResponse {
        private Long id;
        private String fullName;
        private BloodGroup bloodGroup;
        private String city;
        private Boolean isAvailable;
        private Integer totalDonations;
        private Boolean eligible;
        private LocalDate nextEligibleDate;

        public PublicDonorResponse() {}

        public PublicDonorResponse(Long id, String fullName, BloodGroup bloodGroup, String city,
                                   Boolean isAvailable, Integer totalDonations, Boolean eligible, LocalDate nextEligibleDate) {
            this.id = id;
            this.fullName = fullName;
            this.bloodGroup = bloodGroup;
            this.city = city;
            this.isAvailable = isAvailable;
            this.totalDonations = totalDonations;
            this.eligible = eligible;
            this.nextEligibleDate = nextEligibleDate;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public Boolean getIsAvailable() { return isAvailable; }
        public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
        public Integer getTotalDonations() { return totalDonations; }
        public void setTotalDonations(Integer totalDonations) { this.totalDonations = totalDonations; }
        public Boolean getEligible() { return eligible; }
        public void setEligible(Boolean eligible) { this.eligible = eligible; }
        public LocalDate getNextEligibleDate() { return nextEligibleDate; }
        public void setNextEligibleDate(LocalDate nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; }

        public static PublicDonorResponseBuilder builder() { return new PublicDonorResponseBuilder(); }

        public static class PublicDonorResponseBuilder {
            private Long id;
            private String fullName;
            private BloodGroup bloodGroup;
            private String city;
            private Boolean isAvailable;
            private Integer totalDonations;
            private Boolean eligible;
            private LocalDate nextEligibleDate;

            public PublicDonorResponseBuilder id(Long id) { this.id = id; return this; }
            public PublicDonorResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public PublicDonorResponseBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
            public PublicDonorResponseBuilder city(String city) { this.city = city; return this; }
            public PublicDonorResponseBuilder isAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; return this; }
            public PublicDonorResponseBuilder totalDonations(Integer totalDonations) { this.totalDonations = totalDonations; return this; }
            public PublicDonorResponseBuilder eligible(Boolean eligible) { this.eligible = eligible; return this; }
            public PublicDonorResponseBuilder nextEligibleDate(LocalDate nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; return this; }

            public PublicDonorResponse build() {
                return new PublicDonorResponse(id, fullName, bloodGroup, city, isAvailable, totalDonations, eligible, nextEligibleDate);
            }
        }
    }

    public static class DetailedDonorResponse {
        private Long id;
        private Long userId;
        private String fullName;
        private String email;
        private String phone;
        private String city;
        private String address;
        private BloodGroup bloodGroup;
        private LocalDate dateOfBirth;
        private String gender;
        private Boolean isAvailable;
        private LocalDate lastDonationDate;
        private Integer totalDonations;
        private Boolean emergencyDonor;
        private Boolean eligible;
        private LocalDate nextEligibleDate;
        private Double latitude;
        private Double longitude;

        public DetailedDonorResponse() {}

        public DetailedDonorResponse(Long id, Long userId, String fullName, String email, String phone,
                                     String city, String address, BloodGroup bloodGroup, LocalDate dateOfBirth,
                                     String gender, Boolean isAvailable, LocalDate lastDonationDate,
                                     Integer totalDonations, Boolean emergencyDonor, Boolean eligible, LocalDate nextEligibleDate,
                                     Double latitude, Double longitude) {
            this.id = id;
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.city = city;
            this.address = address;
            this.bloodGroup = bloodGroup;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.isAvailable = isAvailable;
            this.lastDonationDate = lastDonationDate;
            this.totalDonations = totalDonations;
            this.emergencyDonor = emergencyDonor;
            this.eligible = eligible;
            this.nextEligibleDate = nextEligibleDate;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
        public LocalDate getDateOfBirth() { return dateOfBirth; }
        public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public Boolean getIsAvailable() { return isAvailable; }
        public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
        public LocalDate getLastDonationDate() { return lastDonationDate; }
        public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }
        public Integer getTotalDonations() { return totalDonations; }
        public void setTotalDonations(Integer totalDonations) { this.totalDonations = totalDonations; }
        public Boolean getEmergencyDonor() { return emergencyDonor; }
        public void setEmergencyDonor(Boolean emergencyDonor) { this.emergencyDonor = emergencyDonor; }
        public Boolean getEligible() { return eligible; }
        public void setEligible(Boolean eligible) { this.eligible = eligible; }
        public LocalDate getNextEligibleDate() { return nextEligibleDate; }
        public void setNextEligibleDate(LocalDate nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; }
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }

        public static DetailedDonorResponseBuilder builder() { return new DetailedDonorResponseBuilder(); }

        public static class DetailedDonorResponseBuilder {
            private Long id;
            private Long userId;
            private String fullName;
            private String email;
            private String phone;
            private String city;
            private String address;
            private BloodGroup bloodGroup;
            private LocalDate dateOfBirth;
            private String gender;
            private Boolean isAvailable;
            private LocalDate lastDonationDate;
            private Integer totalDonations;
            private Boolean emergencyDonor;
            private Boolean eligible;
            private LocalDate nextEligibleDate;
            private Double latitude;
            private Double longitude;

            public DetailedDonorResponseBuilder id(Long id) { this.id = id; return this; }
            public DetailedDonorResponseBuilder userId(Long userId) { this.userId = userId; return this; }
            public DetailedDonorResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
            public DetailedDonorResponseBuilder email(String email) { this.email = email; return this; }
            public DetailedDonorResponseBuilder phone(String phone) { this.phone = phone; return this; }
            public DetailedDonorResponseBuilder city(String city) { this.city = city; return this; }
            public DetailedDonorResponseBuilder address(String address) { this.address = address; return this; }
            public DetailedDonorResponseBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
            public DetailedDonorResponseBuilder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
            public DetailedDonorResponseBuilder gender(String gender) { this.gender = gender; return this; }
            public DetailedDonorResponseBuilder isAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; return this; }
            public DetailedDonorResponseBuilder lastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; return this; }
            public DetailedDonorResponseBuilder totalDonations(Integer totalDonations) { this.totalDonations = totalDonations; return this; }
            public DetailedDonorResponseBuilder emergencyDonor(Boolean emergencyDonor) { this.emergencyDonor = emergencyDonor; return this; }
            public DetailedDonorResponseBuilder eligible(Boolean eligible) { this.eligible = eligible; return this; }
            public DetailedDonorResponseBuilder nextEligibleDate(LocalDate nextEligibleDate) { this.nextEligibleDate = nextEligibleDate; return this; }
            public DetailedDonorResponseBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
            public DetailedDonorResponseBuilder longitude(Double longitude) { this.longitude = longitude; return this; }

            public DetailedDonorResponse build() {
                return new DetailedDonorResponse(id, userId, fullName, email, phone, city, address, bloodGroup, dateOfBirth, gender, isAvailable, lastDonationDate, totalDonations, emergencyDonor, eligible, nextEligibleDate, latitude, longitude);
            }
        }
    }

    public static class AvailabilityUpdateRequest {
        private Boolean isAvailable;

        public AvailabilityUpdateRequest() {}
        public AvailabilityUpdateRequest(Boolean isAvailable) { this.isAvailable = isAvailable; }
        public Boolean getIsAvailable() { return isAvailable; }
        public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
    }

    public static class LocationUpdateRequest {
        private Double latitude;
        private Double longitude;

        public LocationUpdateRequest() {}
        public LocationUpdateRequest(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
    }
}
