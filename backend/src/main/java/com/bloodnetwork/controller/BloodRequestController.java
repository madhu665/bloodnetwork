package com.bloodnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloodnetwork.dto.BloodRequestDto;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.entity.enums.RequestStatus;
import com.bloodnetwork.entity.enums.UrgencyLevel;
import com.bloodnetwork.security.CustomUserDetails;
import com.bloodnetwork.service.BloodRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping
    public ResponseEntity<BloodRequestDto.Response> createRequest(
            @Valid @RequestBody BloodRequestDto.CreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ResponseEntity<>(bloodRequestService.createRequest(request, userDetails.getUser()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BloodRequestDto.Response>> filterRequests(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) UrgencyLevel urgency,
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city) {

        BloodGroup bg = null;
        if (bloodGroup != null && !bloodGroup.trim().isEmpty()) {
            bg = BloodGroup.fromString(bloodGroup);
        }

        return ResponseEntity.ok(bloodRequestService.filterRequests(status, urgency, bg, city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequestDto.Response> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodRequestService.getRequestById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BloodRequestDto.Response> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody BloodRequestDto.StatusUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bloodRequestService.updateStatus(id, request.getStatus(), userDetails.getUser()));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<BloodRequestDto.Response> acceptRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bloodRequestService.acceptRequest(id, userDetails.getUser()));
    }

    @GetMapping("/me")
    public ResponseEntity<List<BloodRequestDto.Response>> getMyRequests(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bloodRequestService.getMyRequests(userDetails.getUser()));
    }

    @GetMapping("/{id}/live-tracking")
    public ResponseEntity<?> getLiveTracking(@PathVariable Long id) {
        return ResponseEntity.ok(bloodRequestService.getLiveTracking(id));
    }
}
