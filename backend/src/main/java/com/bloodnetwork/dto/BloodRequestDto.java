package com.bloodnetwork.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.UrgencyLevel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BloodRequestDto {

    public static class CreateRequest {
        @NotBlank(message = "Patient name is required")
        private String patientName;

        @NotNull(message = "Blood group is required")
        private BloodGroup bloodGroup;

        @NotNull(message = "Units required must be specified")
        @Min(value = 1, message = "At least 1 unit is required")
        private Integer unitsRequired;

        @NotNull(message = "Urgency level is required")
        private UrgencyLevel urgency;

        @NotBlank(message = "Hospital name is required")
        private String hospitalName;

        private String hospitalAddress;

        @NotBlank(message = "City is required")
        private String city;

        @NotNull(message = "Required date must be specified")
        private LocalDate requiredDate;

        @NotBlank(message = "Contact phone is required")
        private String contactPhone;

        private String additionalNotes;

        public CreateRequest() {}

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }
        public BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
        public Integer getUnitsRequired() { return unitsRequired; }
        public void setUnitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; }
        public UrgencyLevel getUrgency() { return urgency; }
        public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }
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
    }

    public static class StatusUpdateRequest {
        @NotNull(message = "Status is required")
        private RequestStatus status;

        public StatusUpdateRequest() {}
        public StatusUpdateRequest(RequestStatus status) { this.status = status; }
        public RequestStatus getStatus() { return status; }
        public void setStatus(RequestStatus status) { this.status = status; }
    }

    public static class Response {
        private Long id;
        private Long requesterId;
        private String requesterName;
        private String patientName;
        private BloodGroup bloodGroup;
        private Integer unitsRequired;
        private UrgencyLevel urgency;
        private RequestStatus status;
        private String hospitalName;
        private String hospitalAddress;
        private String city;
        private LocalDate requiredDate;
        private String contactPhone;
        private String additionalNotes;
        private Integer matchedDonorCount;
        private Long acceptedDonorId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response() {}

        public Response(Long id, Long requesterId, String requesterName, String patientName,
                        BloodGroup bloodGroup, Integer unitsRequired, UrgencyLevel urgency,
                        RequestStatus status, String hospitalName, String hospitalAddress,
                        String city, LocalDate requiredDate, String contactPhone,
                        String additionalNotes, Integer matchedDonorCount, Long acceptedDonorId,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.requesterId = requesterId;
            this.requesterName = requesterName;
            this.patientName = patientName;
            this.bloodGroup = bloodGroup;
            this.unitsRequired = unitsRequired;
            this.urgency = urgency;
            this.status = status;
            this.hospitalName = hospitalName;
            this.hospitalAddress = hospitalAddress;
            this.city = city;
            this.requiredDate = requiredDate;
            this.contactPhone = contactPhone;
            this.additionalNotes = additionalNotes;
            this.matchedDonorCount = matchedDonorCount;
            this.acceptedDonorId = acceptedDonorId;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getRequesterId() { return requesterId; }
        public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
        public String getRequesterName() { return requesterName; }
        public void setRequesterName(String requesterName) { this.requesterName = requesterName; }
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
        public Long getAcceptedDonorId() { return acceptedDonorId; }
        public void setAcceptedDonorId(Long acceptedDonorId) { this.acceptedDonorId = acceptedDonorId; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

        public static ResponseBuilder builder() { return new ResponseBuilder(); }

        public static class ResponseBuilder {
            private Long id;
            private Long requesterId;
            private String requesterName;
            private String patientName;
            private BloodGroup bloodGroup;
            private Integer unitsRequired;
            private UrgencyLevel urgency;
            private RequestStatus status;
            private String hospitalName;
            private String hospitalAddress;
            private String city;
            private LocalDate requiredDate;
            private String contactPhone;
            private String additionalNotes;
            private Integer matchedDonorCount;
            private Long acceptedDonorId;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;

            public ResponseBuilder id(Long id) { this.id = id; return this; }
            public ResponseBuilder requesterId(Long requesterId) { this.requesterId = requesterId; return this; }
            public ResponseBuilder requesterName(String requesterName) { this.requesterName = requesterName; return this; }
            public ResponseBuilder patientName(String patientName) { this.patientName = patientName; return this; }
            public ResponseBuilder bloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; return this; }
            public ResponseBuilder unitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; return this; }
            public ResponseBuilder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
            public ResponseBuilder status(RequestStatus status) { this.status = status; return this; }
            public ResponseBuilder hospitalName(String hospitalName) { this.hospitalName = hospitalName; return this; }
            public ResponseBuilder hospitalAddress(String hospitalAddress) { this.hospitalAddress = hospitalAddress; return this; }
            public ResponseBuilder city(String city) { this.city = city; return this; }
            public ResponseBuilder requiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; return this; }
            public ResponseBuilder contactPhone(String contactPhone) { this.contactPhone = contactPhone; return this; }
            public ResponseBuilder additionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; return this; }
            public ResponseBuilder matchedDonorCount(Integer matchedDonorCount) { this.matchedDonorCount = matchedDonorCount; return this; }
            public ResponseBuilder acceptedDonorId(Long acceptedDonorId) { this.acceptedDonorId = acceptedDonorId; return this; }
            public ResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
            public ResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

            public Response build() {
                return new Response(id, requesterId, requesterName, patientName, bloodGroup, unitsRequired, urgency, status, hospitalName, hospitalAddress, city, requiredDate, contactPhone, additionalNotes, matchedDonorCount, acceptedDonorId, createdAt, updatedAt);
            }
        }
    }
}
