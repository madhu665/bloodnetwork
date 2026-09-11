package com.bloodnetwork.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_request_id")
    private BloodRequest bloodRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;

    @Column(nullable = false)
    private Integer units = 1;

    @Column(nullable = false)
    private LocalDate donationDate;

    private String donationLocation;

    private Boolean verified = true;

    private LocalDateTime createdAt;

    public Donation() {}

    public Donation(Long id, User donor, BloodRequest bloodRequest, BloodBank bloodBank,
                    Integer units, LocalDate donationDate, String donationLocation,
                    Boolean verified, LocalDateTime createdAt) {
        this.id = id;
        this.donor = donor;
        this.bloodRequest = bloodRequest;
        this.bloodBank = bloodBank;
        this.units = units != null ? units : 1;
        this.donationDate = donationDate;
        this.donationLocation = donationLocation;
        this.verified = verified != null ? verified : true;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }

    public BloodRequest getBloodRequest() { return bloodRequest; }
    public void setBloodRequest(BloodRequest bloodRequest) { this.bloodRequest = bloodRequest; }

    public BloodBank getBloodBank() { return bloodBank; }
    public void setBloodBank(BloodBank bloodBank) { this.bloodBank = bloodBank; }

    public Integer getUnits() { return units; }
    public void setUnits(Integer units) { this.units = units; }

    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }

    public String getDonationLocation() { return donationLocation; }
    public void setDonationLocation(String donationLocation) { this.donationLocation = donationLocation; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.units == null) this.units = 1;
        if (this.verified == null) this.verified = true;
    }

    public static DonationBuilder builder() {
        return new DonationBuilder();
    }

    public static class DonationBuilder {
        private Long id;
        private User donor;
        private BloodRequest bloodRequest;
        private BloodBank bloodBank;
        private Integer units = 1;
        private LocalDate donationDate;
        private String donationLocation;
        private Boolean verified = true;
        private LocalDateTime createdAt;

        public DonationBuilder id(Long id) { this.id = id; return this; }
        public DonationBuilder donor(User donor) { this.donor = donor; return this; }
        public DonationBuilder bloodRequest(BloodRequest bloodRequest) { this.bloodRequest = bloodRequest; return this; }
        public DonationBuilder bloodBank(BloodBank bloodBank) { this.bloodBank = bloodBank; return this; }
        public DonationBuilder units(Integer units) { this.units = units; return this; }
        public DonationBuilder donationDate(LocalDate donationDate) { this.donationDate = donationDate; return this; }
        public DonationBuilder donationLocation(String donationLocation) { this.donationLocation = donationLocation; return this; }
        public DonationBuilder verified(Boolean verified) { this.verified = verified; return this; }
        public DonationBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Donation build() {
            return new Donation(id, donor, bloodRequest, bloodBank, units, donationDate, donationLocation, verified, createdAt);
        }
    }
}
