package com.bloodnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bloodnetwork.entity.Donation;
import com.bloodnetwork.entity.User;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorOrderByDonationDateDesc(User donor);

    List<Donation> findByDonorIdOrderByDonationDateDesc(Long donorId);

    long countByDonorId(Long donorId);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.verified = true")
    long countVerifiedDonations();

    @Query("SELECT SUM(d.units) FROM Donation d WHERE d.verified = true")
    Long sumTotalUnitsDonated();
}
