package com.bloodnetwork.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "blood_banks")
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String bloodBankName;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String contactPerson;

    private String operatingHours;

    private Double latitude;

    private Double longitude;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BloodBank() {}

    public BloodBank(Long id, User user, String bloodBankName, String registrationNumber,
                     String contactPerson, String operatingHours, Double latitude, Double longitude,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.bloodBankName = bloodBankName;
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
        this.operatingHours = operatingHours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getBloodBankName() { return bloodBankName; }
    public void setBloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getOperatingHours() { return operatingHours; }
    public void setOperatingHours(String operatingHours) { this.operatingHours = operatingHours; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static BloodBankBuilder builder() {
        return new BloodBankBuilder();
    }

    public static class BloodBankBuilder {
        private Long id;
        private User user;
        private String bloodBankName;
        private String registrationNumber;
        private String contactPerson;
        private String operatingHours;
        private Double latitude;
        private Double longitude;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BloodBankBuilder id(Long id) { this.id = id; return this; }
        public BloodBankBuilder user(User user) { this.user = user; return this; }
        public BloodBankBuilder bloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; return this; }
        public BloodBankBuilder registrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; return this; }
        public BloodBankBuilder contactPerson(String contactPerson) { this.contactPerson = contactPerson; return this; }
        public BloodBankBuilder operatingHours(String operatingHours) { this.operatingHours = operatingHours; return this; }
        public BloodBankBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public BloodBankBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public BloodBankBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BloodBankBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public BloodBank build() {
            return new BloodBank(id, user, bloodBankName, registrationNumber, contactPerson, operatingHours, latitude, longitude, createdAt, updatedAt);
        }
    }
}
