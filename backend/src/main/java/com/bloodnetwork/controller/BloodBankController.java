package com.bloodnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bloodnetwork.dto.BloodInventoryDto;
import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.security.CustomUserDetails;
import com.bloodnetwork.service.BloodBankService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blood-banks")
public class BloodBankController {

    @Autowired
    private BloodBankService bloodBankService;

    @GetMapping
    public ResponseEntity<List<BloodInventoryDto.BloodBankSummary>> getAllBloodBanks(
            @RequestParam(required = false) String city) {
        return ResponseEntity.ok(bloodBankService.getAllBloodBanks(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodInventoryDto.BloodBankSummary> getBloodBankById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodBankService.getBloodBankById(id));
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<List<BloodInventoryDto.InventoryResponse>> getInventory(@PathVariable Long id) {
        return ResponseEntity.ok(bloodBankService.getInventory(id));
    }

    @GetMapping("/me")
    public ResponseEntity<BloodInventoryDto.BloodBankSummary> getMyBloodBank(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        BloodBank bank = bloodBankService.getMyBloodBank(userDetails.getUser());
        return ResponseEntity.ok(bloodBankService.getBloodBankById(bank.getId()));
    }

    @GetMapping("/me/inventory")
    public ResponseEntity<List<BloodInventoryDto.InventoryResponse>> getMyInventory(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        BloodBank bank = bloodBankService.getMyBloodBank(userDetails.getUser());
        return ResponseEntity.ok(bloodBankService.getInventory(bank.getId()));
    }

    @PutMapping("/me/inventory")
    public ResponseEntity<BloodInventoryDto.InventoryResponse> updateMyInventory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody BloodInventoryDto.UpdateInventoryRequest request) {
        BloodBank bank = bloodBankService.getMyBloodBank(userDetails.getUser());
        return ResponseEntity.ok(bloodBankService.updateInventory(bank.getId(), request));
    }
}
