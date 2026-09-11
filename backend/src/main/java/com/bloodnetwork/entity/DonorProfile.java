package com.bloodnetwork.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bloodnetwork.entity.enums.BloodGroup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "donor_profiles")
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    private LocalDate dateOfBirth;

    private String gender;

    private Boolean isAvailable = true;

    private LocalDate lastDonationDate;

    private Integer totalDonations = 0;

    private Boolean emergencyDonor = true;

    private Double latitude;

    private Double longitude;

    private LocalDateTime lastLocationUpdate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public DonorProfile() {}

    public DonorProfile(Long id, User user, BloodGroup bloodGroup, LocalDate dateOfBirth, String gender,
                        Boolean isAvailable, LocalDate lastDonationDate, Integer totalDonations,
                        Boolean emergencyDonor, Double latitude, Double longitude, LocalDateTime lastLocationUpdate,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.isAvailable = isAvailable != null ? isAvailable : true;
        this.lastDonationDate = lastDonationDate;
        this.totalDonations = totalDonations != null ? totalDonations : 0;
        this.emergencyDonor = emergencyDonor != null ? emergencyDonor : true;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastLocationUpdate = lastLocationUpdate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getLastLocationUpdate() { return lastLocationUpdate; }
    public void setLastLocationUpdate(LocalDateTime lastLocationUpdate) { this.lastLocationUpdate = lastLocationUpdate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isEligibleToDonate() {
        if (!Boolean.TRUE.equals(isAvailable)) {
            return false;
        }
        if (lastDonationDate == null) {
            return true;
        }
        return LocalDate.now().isAfter(lastDonationDate.plusDays(90));
    }

    public LocalDate getNextEligibleDate() {
        if (lastDonationDate == null) {
            return LocalDate.now();
        }
        return lastDonationDate.plusDays(90);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isAvailable == null) this.isAvailable = true;
        if (this.totalDonations == null) this.totalDonations = 0;
        if (this.emergencyDonor == null) this.emergencyDonor = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static DonorProfileBuilder builder() {
        return new DonorProfileBuilder();
    }

    public static class DonorProfileBuilder {
        private Long id;
        private User user;
        private BloodGroup bloodGroup;
        private LocalDate dateOfBirth;
        private String gender;
        private Boolean isAvailable = true;
        private LocalDate lastDonationDate;
        private Integer totalDonations = 0;
        private Boolean emergencyDonor = true;
        private Double latitude;
        private Double longitude;
        private LocalDateTime lastLocationUpdate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DonorProfileBuilder id(Long id) { this.id = id; return this; }
        public DonorProfileBuilder user(User user) { this.user = user; return this; }
        public DonorProfileBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
        public DonorProfileBuilder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
        public DonorProfileBuilder gender(String gender) { this.gender = gender; return this; }
        public DonorProfileBuilder isAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; return this; }
        public DonorProfileBuilder lastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; return this; }
        public DonorProfileBuilder totalDonations(Integer totalDonations) { this.totalDonations = totalDonations; return this; }
        public DonorProfileBuilder emergencyDonor(Boolean emergencyDonor) { this.emergencyDonor = emergencyDonor; return this; }
        public DonorProfileBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public DonorProfileBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public DonorProfileBuilder lastLocationUpdate(LocalDateTime lastLocationUpdate) { this.lastLocationUpdate = lastLocationUpdate; return this; }
        public DonorProfileBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public DonorProfileBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public DonorProfile build() {
            return new DonorProfile(id, user, bloodGroup, dateOfBirth, gender, isAvailable, lastDonationDate, totalDonations, emergencyDonor, latitude, longitude, lastLocationUpdate, createdAt, updatedAt);
        }
    }
}
