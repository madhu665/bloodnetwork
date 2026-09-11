package com.bloodnetwork.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.UrgencyLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "blood_requests")
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(nullable = false)
    private String patientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private Integer unitsRequired;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyLevel urgency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accepted_donor_id")
    private User acceptedDonor;

    @Column(nullable = false)
    private String hospitalName;

    private String hospitalAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private LocalDate requiredDate;

    @Column(nullable = false)
    private String contactPhone;

    @Column(length = 1000)
    private String additionalNotes;

    private Integer matchedDonorCount = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BloodRequest() {}

    public BloodRequest(Long id, User requester, String patientName, BloodGroup bloodGroup,
                        Integer unitsRequired, UrgencyLevel urgency, RequestStatus status, User acceptedDonor,
                        String hospitalName, String hospitalAddress, String city, LocalDate requiredDate,
                        String contactPhone, String additionalNotes, Integer matchedDonorCount,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requester = requester;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.unitsRequired = unitsRequired;
        this.urgency = urgency != null ? urgency : UrgencyLevel.NORMAL;
        this.status = status != null ? status : RequestStatus.PENDING;
        this.acceptedDonor = acceptedDonor;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.city = city;
        this.requiredDate = requiredDate;
        this.contactPhone = contactPhone;
        this.additionalNotes = additionalNotes;
        this.matchedDonorCount = matchedDonorCount != null ? matchedDonorCount : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public Integer getUnitsRequired() { return unitsRequired; }
    public void setUnitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; }

    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public User getAcceptedDonor() { return acceptedDonor; }
    public void setAcceptedDonor(User acceptedDonor) { this.acceptedDonor = acceptedDonor; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getHospitalAddress() { return hospitalAddress; }
    public void setHospitalAddress(String hospitalAddress) { this.hospitalAddress = hospitalAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public Integer getMatchedDonorCount() { return matchedDonorCount; }
    public void setMatchedDonorCount(Integer matchedDonorCount) { this.matchedDonorCount = matchedDonorCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = RequestStatus.PENDING;
        if (this.urgency == null) this.urgency = UrgencyLevel.NORMAL;
        if (this.matchedDonorCount == null) this.matchedDonorCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static BloodRequestBuilder builder() {
        return new BloodRequestBuilder();
    }

    public static class BloodRequestBuilder {
        private Long id;
        private User requester;
        private String patientName;
        private BloodGroup bloodGroup;
        private Integer unitsRequired;
        private UrgencyLevel urgency = UrgencyLevel.NORMAL;
        private RequestStatus status = RequestStatus.PENDING;
        private User acceptedDonor;
        private String hospitalName;
        private String hospitalAddress;
        private String city;
        private LocalDate requiredDate;
        private String contactPhone;
        private String additionalNotes;
        private Integer matchedDonorCount = 0;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BloodRequestBuilder id(Long id) { this.id = id; return this; }
        public BloodRequestBuilder requester(User requester) { this.requester = requester; return this; }
        public BloodRequestBuilder patientName(String patientName) { this.patientName = patientName; return this; }
        public BloodRequestBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
        public BloodRequestBuilder unitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; return this; }
        public BloodRequestBuilder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
        public BloodRequestBuilder status(RequestStatus status) { this.status = status; return this; }
        public BloodRequestBuilder acceptedDonor(User acceptedDonor) { this.acceptedDonor = acceptedDonor; return this; }
        public BloodRequestBuilder hospitalName(String hospitalName) { this.hospitalName = hospitalName; return this; }
        public BloodRequestBuilder hospitalAddress(String hospitalAddress) { this.hospitalAddress = hospitalAddress; return this; }
        public BloodRequestBuilder city(String city) { this.city = city; return this; }
        public BloodRequestBuilder requiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; return this; }
        public BloodRequestBuilder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
        public BloodRequestBuilder additionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; return this; }
        public BloodRequestBuilder matchedDonorCount(Integer matchedDonorCount) { this.matchedDonorCount = matchedDonorCount; return this; }
        public BloodRequestBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BloodRequestBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public BloodRequest build() {
            return new BloodRequest(id, requester, patientName, bloodGroup, unitsRequired, urgency, status, acceptedDonor, hospitalName, hospitalAddress, city, requiredDate, contactPhone, additionalNotes, matchedDonorCount, createdAt, updatedAt);
        }
    }
}
