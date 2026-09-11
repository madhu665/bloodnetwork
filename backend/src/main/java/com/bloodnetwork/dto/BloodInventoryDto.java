package com.bloodnetwork.dto;

import java.time.LocalDateTime;

import com.bloodnetwork.entity.enums.BloodGroup;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BloodInventoryDto {

    public static class InventoryResponse {
        private Long id;
        private Long bloodBankId;
        private String bloodBankName;
        private BloodGroup bloodGroup;
        private Integer availableUnits;
        private Integer reservedUnits;
        private Integer criticalThresholdUnits;
        private Boolean critical;
        private LocalDateTime lastRestockedAt;

        public InventoryResponse() {}

        public InventoryResponse(Long id, Long bloodBankId, String bloodBankName, BloodGroup bloodGroup,
                                 Integer availableUnits, Integer reservedUnits, Integer criticalThresholdUnits,
                                 Boolean critical, LocalDateTime lastRestockedAt) {
            this.id = id;
            this.bloodBankId = bloodBankId;
            this.bloodBankName = bloodBankName;
            this.bloodGroup = bloodGroup;
            this.availableUnits = availableUnits;
            this.reservedUnits = reservedUnits;
            this.criticalThresholdUnits = criticalThresholdUnits;
            this.critical = critical;
            this.lastRestockedAt = lastRestockedAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getBloodBankId() { return bloodBankId; }
        public void setBloodBankId(Long bloodBankId) { this.bloodBankId = bloodBankId; }
        public String getBloodBankName() { return bloodBankName; }
        public void setBloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; }
        public BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
        public Integer getAvailableUnits() { return availableUnits; }
        public void setAvailableUnits(Integer availableUnits) { this.availableUnits = availableUnits; }
        public Integer getReservedUnits() { return reservedUnits; }
        public void setReservedUnits(Integer reservedUnits) { this.reservedUnits = reservedUnits; }
        public Integer getCriticalThresholdUnits() { return criticalThresholdUnits; }
        public void setCriticalThresholdUnits(Integer criticalThresholdUnits) { this.criticalThresholdUnits = criticalThresholdUnits; }
        public Boolean getCritical() { return critical; }
        public void setCritical(Boolean critical) { this.critical = critical; }
        public LocalDateTime getLastRestockedAt() { return lastRestockedAt; }
        public void setLastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; }

        public static InventoryResponseBuilder builder() { return new InventoryResponseBuilder(); }

        public static class InventoryResponseBuilder {
            private Long id;
            private Long bloodBankId;
            private String bloodBankName;
            private BloodGroup bloodGroup;
            private Integer availableUnits;
            private Integer reservedUnits;
            private Integer criticalThresholdUnits;
            private Boolean critical;
            private LocalDateTime lastRestockedAt;

            public InventoryResponseBuilder id(Long id) { this.id = id; return this; }
            public InventoryResponseBuilder bloodBankId(Long bloodBankId) { this.bloodBankId = bloodBankId; return this; }
            public InventoryResponseBuilder bloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; return this; }
            public InventoryResponseBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
            public InventoryResponseBuilder availableUnits(Integer availableUnits) { this.availableUnits = availableUnits; return this; }
            public InventoryResponseBuilder reservedUnits(Integer reservedUnits) { this.reservedUnits = reservedUnits; return this; }
            public InventoryResponseBuilder criticalThresholdUnits(Integer criticalThresholdUnits) { this.criticalThresholdUnits = criticalThresholdUnits; return this; }
            public InventoryResponseBuilder critical(Boolean critical) { this.critical = critical; return this; }
            public InventoryResponseBuilder lastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; return this; }

            public InventoryResponse build() {
                return new InventoryResponse(id, bloodBankId, bloodBankName, bloodGroup, availableUnits, reservedUnits, criticalThresholdUnits, critical, lastRestockedAt);
            }
        }
    }

    public static class UpdateInventoryRequest {
        @NotNull(message = "Blood group is required")
        private BloodGroup bloodGroup;

        @NotNull(message = "Available units is required")
        @Min(value = 0, message = "Available units cannot be negative")
        private Integer availableUnits;

        @Min(value = 0, message = "Reserved units cannot be negative")
        private Integer reservedUnits;

        @Min(value = 1, message = "Critical threshold must be at least 1")
        private Integer criticalThresholdUnits;

        public UpdateInventoryRequest() {}

        public BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
        public Integer getAvailableUnits() { return availableUnits; }
        public void setAvailableUnits(Integer availableUnits) { this.availableUnits = availableUnits; }
        public Integer getReservedUnits() { return reservedUnits; }
        public void setReservedUnits(Integer reservedUnits) { this.reservedUnits = reservedUnits; }
        public Integer getCriticalThresholdUnits() { return criticalThresholdUnits; }
        public void setCriticalThresholdUnits(Integer criticalThresholdUnits) { this.criticalThresholdUnits = criticalThresholdUnits; }
    }

    public static class BloodBankSummary {
        private Long id;
        private String bloodBankName;
        private String registrationNumber;
        private String contactPerson;
        private String phone;
        private String email;
        private String city;
        private String address;
        private String operatingHours;
        private Integer totalUnits;

        public BloodBankSummary() {}

        public BloodBankSummary(Long id, String bloodBankName, String registrationNumber, String contactPerson,
                                String phone, String email, String city, String address, String operatingHours, Integer totalUnits) {
            this.id = id;
            this.bloodBankName = bloodBankName;
            this.registrationNumber = registrationNumber;
            this.contactPerson = contactPerson;
            this.phone = phone;
            this.email = email;
            this.city = city;
            this.address = address;
            this.operatingHours = operatingHours;
            this.totalUnits = totalUnits;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getBloodBankName() { return bloodBankName; }
        public void setBloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; }
        public String getRegistrationNumber() { return registrationNumber; }
        public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
        public String getContactPerson() { return contactPerson; }
        public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getOperatingHours() { return operatingHours; }
        public void setOperatingHours(String operatingHours) { this.operatingHours = operatingHours; }
        public Integer getTotalUnits() { return totalUnits; }
        public void setTotalUnits(Integer totalUnits) { this.totalUnits = totalUnits; }

        public static BloodBankSummaryBuilder builder() { return new BloodBankSummaryBuilder(); }

        public static class BloodBankSummaryBuilder {
            private Long id;
            private String bloodBankName;
            private String registrationNumber;
            private String contactPerson;
            private String phone;
            private String email;
            private String city;
            private String address;
            private String operatingHours;
            private Integer totalUnits;

            public BloodBankSummaryBuilder id(Long id) { this.id = id; return this; }
            public BloodBankSummaryBuilder bloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; return this; }
            public BloodBankSummaryBuilder registrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; return this; }
            public BloodBankSummaryBuilder contactPerson(String contactPerson) { this.contactPerson = contactPerson; return this; }
            public BloodBankSummaryBuilder phone(String phone) { this.phone = phone; return this; }
            public BloodBankSummaryBuilder email(String email) { this.email = email; return this; }
            public BloodBankSummaryBuilder city(String city) { this.city = city; return this; }
            public BloodBankSummaryBuilder address(String address) { this.address = address; return this; }
            public BloodBankSummaryBuilder operatingHours(String operatingHours) { this.operatingHours = operatingHours; return this; }
            public BloodBankSummaryBuilder totalUnits(Integer totalUnits) { this.totalUnits = totalUnits; return this; }

            public BloodBankSummary build() {
                return new BloodBankSummary(id, bloodBankName, registrationNumber, contactPerson, phone, email, city, address, operatingHours, totalUnits);
            }
        }
    }
}
