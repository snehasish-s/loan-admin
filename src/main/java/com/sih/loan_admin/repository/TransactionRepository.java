package com.sih.loan_admin.repository;

import com.sih.loan_admin.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAiFraudStatus(String status);   // Get suspicious transactions
    List<Transaction> findByBeneficiaryId(Long beneficiaryId); // Get transactions per beneficiary
}
