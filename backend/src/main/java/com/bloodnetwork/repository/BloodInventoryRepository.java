package com.bloodnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.BloodBank;
import com.bloodnetwork.entity.BloodInventory;
import com.bloodnetwork.entity.enums.BloodGroup;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {

    List<BloodInventory> findByBloodBank(BloodBank bloodBank);

    List<BloodInventory> findByBloodBankId(Long bloodBankId);

    Optional<BloodInventory> findByBloodBankIdAndBloodGroup(Long bloodBankId, BloodGroup bloodGroup);

    @Query("SELECT SUM(i.availableUnits) FROM BloodInventory i")
    Long getTotalAvailableUnits();

    @Query("SELECT SUM(i.availableUnits) FROM BloodInventory i WHERE i.bloodGroup = :bloodGroup")
    Long getTotalAvailableUnitsByBloodGroup(BloodGroup bloodGroup);
}
