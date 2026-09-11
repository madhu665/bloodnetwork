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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "blood_inventories",
    uniqueConstraints = @UniqueConstraint(columnNames = {"blood_bank_id", "blood_group"})
)
public class BloodInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_bank_id", nullable = false)
    private BloodBank bloodBank;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private Integer availableUnits = 0;

    @Column(nullable = false)
    private Integer reservedUnits = 0;

    @Column(nullable = false)
    private Integer criticalThresholdUnits = 5;

    private LocalDateTime lastRestockedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BloodInventory() {}

    public BloodInventory(Long id, BloodBank bloodBank, BloodGroup bloodGroup, Integer availableUnits,
                          Integer reservedUnits, Integer criticalThresholdUnits, LocalDateTime lastRestockedAt,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bloodBank = bloodBank;
        this.bloodGroup = bloodGroup;
        this.availableUnits = availableUnits != null ? availableUnits : 0;
        this.reservedUnits = reservedUnits != null ? reservedUnits : 0;
        this.criticalThresholdUnits = criticalThresholdUnits != null ? criticalThresholdUnits : 5;
        this.lastRestockedAt = lastRestockedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BloodBank getBloodBank() { return bloodBank; }
    public void setBloodBank(BloodBank bloodBank) { this.bloodBank = bloodBank; }

    public BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public Integer getAvailableUnits() { return availableUnits; }
    public void setAvailableUnits(Integer availableUnits) { this.availableUnits = availableUnits; }

    public Integer getReservedUnits() { return reservedUnits; }
    public void setReservedUnits(Integer reservedUnits) { this.reservedUnits = reservedUnits; }

    public Integer getCriticalThresholdUnits() { return criticalThresholdUnits; }
    public void setCriticalThresholdUnits(Integer criticalThresholdUnits) { this.criticalThresholdUnits = criticalThresholdUnits; }

    public LocalDateTime getLastRestockedAt() { return lastRestockedAt; }
    public void setLastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isCritical() {
        return availableUnits != null && criticalThresholdUnits != null && availableUnits <= criticalThresholdUnits;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.availableUnits == null) this.availableUnits = 0;
        if (this.reservedUnits == null) this.reservedUnits = 0;
        if (this.criticalThresholdUnits == null) this.criticalThresholdUnits = 5;
        if (this.lastRestockedAt == null) this.lastRestockedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static BloodInventoryBuilder builder() {
        return new BloodInventoryBuilder();
    }

    public static class BloodInventoryBuilder {
        private Long id;
        private BloodBank bloodBank;
        private BloodGroup bloodGroup;
        private Integer availableUnits = 0;
        private Integer reservedUnits = 0;
        private Integer criticalThresholdUnits = 5;
        private LocalDateTime lastRestockedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public BloodInventoryBuilder id(Long id) { this.id = id; return this; }
        public BloodInventoryBuilder bloodBank(BloodBank bloodBank) { this.bloodBank = bloodBank; return this; }
        public BloodInventoryBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
        public BloodInventoryBuilder availableUnits(Integer availableUnits) { this.availableUnits = availableUnits; return this; }
        public BloodInventoryBuilder reservedUnits(Integer reservedUnits) { this.reservedUnits = reservedUnits; return this; }
        public BloodInventoryBuilder criticalThresholdUnits(Integer criticalThresholdUnits) { this.criticalThresholdUnits = criticalThresholdUnits; return this; }
        public BloodInventoryBuilder lastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; return this; }
        public BloodInventoryBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BloodInventoryBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public BloodInventory build() {
            return new BloodInventory(id, bloodBank, bloodGroup, availableUnits, reservedUnits, criticalThresholdUnits, lastRestockedAt, createdAt, updatedAt);
        }
    }
}
