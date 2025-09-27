package com.sih.loan_admin.repository;

import com.sih.loan_admin.Model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    List<Beneficiary> findByStatus(String status); // ✅ Now available
}
