package com.bloodnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloodnetwork.dto.DashboardStatsDto;
import com.bloodnetwork.dto.DonorDto;
import com.bloodnetwork.entity.Donation;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.security.CustomUserDetails;
import com.bloodnetwork.service.DonorService;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/search")
    public ResponseEntity<List<DonorDto.PublicDonorResponse>> searchDonors(
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) String city) {

        BloodGroup bg = null;
        if (bloodGroup != null && !bloodGroup.trim().isEmpty()) {
            bg = BloodGroup.fromString(bloodGroup);
        }

        return ResponseEntity.ok(donorService.searchDonors(bg, city));
    }

    @GetMapping("/me")
    public ResponseEntity<DonorDto.DetailedDonorResponse> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(donorService.getDonorProfile(userDetails.getUser()));
    }

    @PutMapping("/me/availability")
    public ResponseEntity<DonorDto.DetailedDonorResponse> updateAvailability(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DonorDto.AvailabilityUpdateRequest req) {
        return ResponseEntity.ok(donorService.updateAvailability(userDetails.getUser(), req.getIsAvailable()));
    }

    @GetMapping("/me/dashboard")
    public ResponseEntity<DashboardStatsDto.DonorStats> getDashboardStats(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(donorService.getDonorDashboardStats(userDetails.getUser()));
    }

    @GetMapping("/me/donations")
    public ResponseEntity<List<Donation>> getDonationHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(donorService.getDonationHistory(userDetails.getUser()));
    }

    @PutMapping("/me/location")
    public ResponseEntity<?> updateLocation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DonorDto.LocationUpdateRequest req) {
        donorService.updateLocation(userDetails.getUser(), req.getLatitude(), req.getLongitude());
        return ResponseEntity.ok().build();
    }
}
