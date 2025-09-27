package com.sih.loan_admin.controller;

import com.sih.loan_admin.Model.AuditLog;
import com.sih.loan_admin.Model.Beneficiary;
import com.sih.loan_admin.Model.Transaction;
import com.sih.loan_admin.repository.AuditLogRepository;
import com.sih.loan_admin.repository.BeneficiaryRepository;
import com.sih.loan_admin.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OfficerControl {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    // ------------------ Dashboard ------------------
    @GetMapping("/")
    public String index() {
        return "index"; // Officer Dashboard
    }

    // ------------------ Pending Transactions ------------------
    @GetMapping("/pending-transactions")
    public String pendingTransactions(Model model) {
        List<Beneficiary> pendingList = beneficiaryRepository.findByStatus("PENDING");
        model.addAttribute("pendingBeneficiaries", pendingList);
        return "pending-transactions";
    }

    // Approve a beneficiary
    @PostMapping("/approve/{id}")
    public String approveBeneficiary(@PathVariable Long id) {
        beneficiaryRepository.findById(id).ifPresent(b -> {
            b.setStatus("APPROVED");
            beneficiaryRepository.save(b);

            // Audit log
            AuditLog log = new AuditLog();
            log.setUsername("Officer"); // Replace with actual officer username
            log.setAction("APPROVE");
            log.setTarget("Beneficiary ID: " + b.getId());
            log.setStatus("SUCCESS");
            log.setRemarks("Approved successfully");
            auditLogRepository.save(log);
        });
        return "redirect:/approve-reject"; 
    }

    // Reject a beneficiary
    @PostMapping("/reject/{id}")
    public String rejectBeneficiary(@PathVariable Long id) {
        beneficiaryRepository.findById(id).ifPresent(b -> {
            b.setStatus("REJECTED");
            beneficiaryRepository.save(b);

            // Audit log
            AuditLog log = new AuditLog();
            log.setUsername("Officer");
            log.setAction("REJECT");
            log.setTarget("Beneficiary ID: " + b.getId());
            log.setStatus("SUCCESS");
            log.setRemarks("Rejected successfully");
            auditLogRepository.save(log);
        });
        return "redirect:/approve-reject"; 
    }

    // ------------------ Approved / Rejected Transactions ------------------
    @GetMapping("/approve-reject")
    public String approveReject(Model model) {
        List<Beneficiary> approvedList = beneficiaryRepository.findByStatus("APPROVED");
        List<Beneficiary> rejectedList = beneficiaryRepository.findByStatus("REJECTED");

        model.addAttribute("approvedBeneficiaries", approvedList);
        model.addAttribute("rejectedBeneficiaries", rejectedList);
        return "approve-reject";
    }

    // ------------------ Wallet Management ------------------
    @GetMapping("/wallet-balances")
    public String walletBalances(Model model) {
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
        model.addAttribute("beneficiaries", beneficiaries);
        return "wallet-balances";
    }

    @PostMapping("/wallet-add/{id}")
    public String addMoneyToWallet(@PathVariable Long id, @RequestParam("amount") Double amount) {
        beneficiaryRepository.findById(id).ifPresent(b -> {
            double currentBalance = b.getWalletBalance() != null ? b.getWalletBalance() : 0.0;
            b.setWalletBalance(currentBalance + amount);
            beneficiaryRepository.save(b);

            // Audit log
            AuditLog log = new AuditLog();
            log.setUsername("Officer");
            log.setAction("WALLET_ADD");
            log.setTarget("Beneficiary ID: " + b.getId());
            log.setStatus("SUCCESS");
            log.setRemarks("Added amount: " + amount);
            auditLogRepository.save(log);
        });
        return "redirect:/wallet-balances";
    }

    // ------------------ Reports ------------------
    @GetMapping("/reports")
    public String generateReports(Model model) {
        List<Beneficiary> allBeneficiaries = beneficiaryRepository.findAll();

        long total = allBeneficiaries.size();
        long pending = allBeneficiaries.stream().filter(b -> "PENDING".equals(b.getStatus())).count();
        long approved = allBeneficiaries.stream().filter(b -> "APPROVED".equals(b.getStatus())).count();
        long rejected = allBeneficiaries.stream().filter(b -> "REJECTED".equals(b.getStatus())).count();
        double totalWallet = allBeneficiaries.stream()
                .mapToDouble(b -> b.getWalletBalance() != null ? b.getWalletBalance() : 0.0)
                .sum();

        model.addAttribute("totalBeneficiaries", total);
        model.addAttribute("pendingCount", pending);
        model.addAttribute("approvedCount", approved);
        model.addAttribute("rejectedCount", rejected);
        model.addAttribute("totalWalletBalance", totalWallet);
        model.addAttribute("beneficiaries", allBeneficiaries);
        return "reports";
    }

    // ------------------ Transaction Details ------------------
    @GetMapping("/view-transaction-details")
    public String viewTransactionDetails(Model model) {
        List<Transaction> transactions = transactionRepository.findAll();
        model.addAttribute("transactions", transactions);
        return "view-transactions"; // Include photo/video/metadata columns in Thymeleaf template
    }

    // ------------------ Audit Logs ------------------
    @GetMapping("/audit-logs")
    public String auditLogs(Model model) {
        List<AuditLog> logs = auditLogRepository.findAll();
        model.addAttribute("auditLogs", logs);
        return "audit-logs";
    }
    
    @GetMapping("/logout")
    public String log() {
    	return "logout";
    
    }
}
