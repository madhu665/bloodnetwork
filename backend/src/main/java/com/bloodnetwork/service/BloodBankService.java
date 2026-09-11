package com.bloodnetwork.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloodnetwork.dto.BloodInventoryDto;
import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.entity.BloodInventory;
import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.BloodGroup;
import com.bloodnetwork.exception.ResourceNotFoundException;
import com.bloodnetwork.repository.BloodBankRepository;
import com.bloodnetwork.repository.BloodInventoryRepository;

@Service
public class BloodBankService {

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    public List<BloodInventoryDto.BloodBankSummary> getAllBloodBanks(String city) {
        List<BloodBank> bloodBanks = bloodBankRepository.searchBloodBanks(city);
        return bloodBanks.stream().map(b -> {
            List<BloodInventory> inventories = bloodInventoryRepository.findByBloodBankId(b.getId());
            int totalUnits = inventories.stream().mapToInt(BloodInventory::getAvailableUnits).sum();

            return BloodInventoryDto.BloodBankSummary.builder()
                    .id(b.getId())
                    .bloodBankName(b.getBloodBankName())
                    .registrationNumber(b.getRegistrationNumber())
                    .contactPerson(b.getContactPerson())
                    .phone(b.getUser().getPhone())
                    .email(b.getUser().getEmail())
                    .city(b.getUser().getCity())
                    .address(b.getUser().getAddress())
                    .operatingHours(b.getOperatingHours())
                    .totalUnits(totalUnits)
                    .build();
        }).collect(Collectors.toList());
    }

    public BloodInventoryDto.BloodBankSummary getBloodBankById(Long id) {
        BloodBank b = bloodBankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood bank not found with ID: " + id));

        List<BloodInventory> inventories = bloodInventoryRepository.findByBloodBankId(b.getId());
        int totalUnits = inventories.stream().mapToInt(BloodInventory::getAvailableUnits).sum();

        return BloodInventoryDto.BloodBankSummary.builder()
                .id(b.getId())
                .bloodBankName(b.getBloodBankName())
                .registrationNumber(b.getRegistrationNumber())
                .contactPerson(b.getContactPerson())
                .phone(b.getUser().getPhone())
                .email(b.getUser().getEmail())
                .city(b.getUser().getCity())
                .address(b.getUser().getAddress())
                .operatingHours(b.getOperatingHours())
                .totalUnits(totalUnits)
                .build();
    }

    public List<BloodInventoryDto.InventoryResponse> getInventory(Long bloodBankId) {
        return bloodInventoryRepository.findByBloodBankId(bloodBankId)
                .stream()
                .map(this::mapToInventoryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BloodInventoryDto.InventoryResponse updateInventory(Long bloodBankId, BloodInventoryDto.UpdateInventoryRequest req) {
        BloodInventory inventory = bloodInventoryRepository.findByBloodBankIdAndBloodGroup(bloodBankId, req.getBloodGroup())
                .orElseGet(() -> {
                    BloodBank bank = bloodBankRepository.findById(bloodBankId)
                            .orElseThrow(() -> new ResourceNotFoundException("Blood bank not found: " + bloodBankId));
                    return BloodInventory.builder()
                            .bloodBank(bank)
                            .bloodGroup(req.getBloodGroup())
                            .build();
                });

        inventory.setAvailableUnits(req.getAvailableUnits());
        if (req.getReservedUnits() != null) {
            inventory.setReservedUnits(req.getReservedUnits());
        }
        if (req.getCriticalThresholdUnits() != null) {
            inventory.setCriticalThresholdUnits(req.getCriticalThresholdUnits());
        }
        inventory.setLastRestockedAt(LocalDateTime.now());

        inventory = bloodInventoryRepository.save(inventory);
        return mapToInventoryDto(inventory);
    }

    public BloodBank getMyBloodBank(User user) {
        return bloodBankRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No blood bank affiliated with account: " + user.getEmail()));
    }

    private BloodInventoryDto.InventoryResponse mapToInventoryDto(BloodInventory i) {
        return BloodInventoryDto.InventoryResponse.builder()
                .id(i.getId())
                .bloodBankId(i.getBloodBank().getId())
                .bloodBankName(i.getBloodBank().getBloodBankName())
                .bloodGroup(i.getBloodGroup())
                .availableUnits(i.getAvailableUnits())
                .reservedUnits(i.getReservedUnits())
                .criticalThresholdUnits(i.getCriticalThresholdUnits())
                .critical(i.isCritical())
                .lastRestockedAt(i.getLastRestockedAt())
                .build();
    }
}
