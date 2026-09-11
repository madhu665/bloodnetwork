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
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String emergencyContactNumber;

    private String department;

    private Integer bedCount;

    private Double latitude;

    private Double longitude;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Hospital() {}

    public Hospital(Long id, User user, String hospitalName, String licenseNumber,
                    String emergencyContactNumber, String department, Integer bedCount,
                    Double latitude, Double longitude, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.hospitalName = hospitalName;
        this.licenseNumber = licenseNumber;
        this.emergencyContactNumber = emergencyContactNumber;
        this.department = department;
        this.bedCount = bedCount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getBedCount() { return bedCount; }
    public void setBedCount(Integer bedCount) { this.bedCount = bedCount; }

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

    public static HospitalBuilder builder() {
        return new HospitalBuilder();
    }

    public static class HospitalBuilder {
        private Long id;
        private User user;
        private String hospitalName;
        private String licenseNumber;
        private String emergencyContactNumber;
        private String department;
        private Integer bedCount;
        private Double latitude;
        private Double longitude;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public HospitalBuilder id(Long id) { this.id = id; return this; }
        public HospitalBuilder user(User user) { this.user = user; return this; }
        public HospitalBuilder hospitalName(String hospitalName) { this.hospitalName = hospitalName; return this; }
        public HospitalBuilder licenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; return this; }
        public HospitalBuilder emergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; return this; }
        public HospitalBuilder department(String department) { this.department = department; return this; }
        public HospitalBuilder bedCount(Integer bedCount) { this.bedCount = bedCount; return this; }
        public HospitalBuilder latitude(Double latitude) { this.latitude = latitude; return this; }
        public HospitalBuilder longitude(Double longitude) { this.longitude = longitude; return this; }
        public HospitalBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public HospitalBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Hospital build() {
            return new Hospital(id, user, hospitalName, licenseNumber, emergencyContactNumber, department, bedCount, latitude, longitude, createdAt, updatedAt);
        }
    }
}
