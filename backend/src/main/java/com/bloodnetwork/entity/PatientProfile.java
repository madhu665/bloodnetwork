package com.bloodnetwork.entity;

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
@Table(name = "patient_profiles")
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private String emergencyContactName;

    private String emergencyContactPhone;

    @Column(length = 1000)
    private String medicalNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PatientProfile() {}

    public PatientProfile(Long id, User user, BloodGroup bloodGroup, String emergencyContactName,
                          String emergencyContactPhone, String medicalNotes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.bloodGroup = bloodGroup;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.medicalNotes = medicalNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }

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

    public static PatientProfileBuilder builder() {
        return new PatientProfileBuilder();
    }

    public static class PatientProfileBuilder {
        private Long id;
        private User user;
        private BloodGroup bloodGroup;
        private String emergencyContactName;
        private String emergencyContactPhone;
        private String medicalNotes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PatientProfileBuilder id(Long id) { this.id = id; return this; }
        public PatientProfileBuilder user(User user) { this.user = user; return this; }
        public PatientProfileBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
        public PatientProfileBuilder emergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; return this; }
        public PatientProfileBuilder emergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; return this; }
        public PatientProfileBuilder medicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; return this; }
        public PatientProfileBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public PatientProfileBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public PatientProfile build() {
            return new PatientProfile(id, user, bloodGroup, emergencyContactName, emergencyContactPhone, medicalNotes, createdAt, updatedAt);
        }
    }
}
