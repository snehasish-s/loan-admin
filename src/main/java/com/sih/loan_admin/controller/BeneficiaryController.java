package com.sih.loan_admin.controller;

import com.sih.loan_admin.Model.Beneficiary;
import com.sih.loan_admin.repository.BeneficiaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BeneficiaryController {

    @Autowired
    private BeneficiaryRepository repository;

    // =====================
    // Thymeleaf Pages
    // =====================

    @GetMapping("/add-beneficiary")
    public String showAddBeneficiaryForm(Model model) {
        model.addAttribute("beneficiary", new Beneficiary());
        return "add-beneficiary";
    }

    @PostMapping("/add-beneficiary")
    public String saveBeneficiary(@ModelAttribute Beneficiary beneficiary, Model model) {
        repository.save(beneficiary);
        model.addAttribute("successMessage", "Beneficiary added successfully!");
        model.addAttribute("beneficiary", new Beneficiary()); // Reset form
        return "add-beneficiary";
    }

    // =====================
    // Optional: REST API Endpoints
    // =====================

    @RestController
    @RequestMapping("/api/beneficiaries")
    static class BeneficiaryRestController {

        @Autowired
        private BeneficiaryRepository repository;

        @PostMapping
        public Beneficiary addBeneficiary(@RequestBody Beneficiary beneficiary) {
            return repository.save(beneficiary);
        }

        @GetMapping
        public List<Beneficiary> getAllBeneficiaries() {
            return repository.findAll();
        }

        @GetMapping("/{id}")
        public Beneficiary getBeneficiaryById(@PathVariable Long id) {
            return repository.findById(id).orElse(null);
        }

        @PutMapping("/{id}")
        public Beneficiary updateBeneficiary(@PathVariable Long id, @RequestBody Beneficiary updatedBeneficiary) {
            return repository.findById(id).map(beneficiary -> {
                beneficiary.setName(updatedBeneficiary.getName());
                beneficiary.setAadhaar(updatedBeneficiary.getAadhaar());
                beneficiary.setBankAccount(updatedBeneficiary.getBankAccount());
                beneficiary.setIfsc(updatedBeneficiary.getIfsc());
                return repository.save(beneficiary);
            }).orElse(null);
        }

        @DeleteMapping("/{id}")
        public String deleteBeneficiary(@PathVariable Long id) {
            repository.deleteById(id);
            return "Beneficiary with ID " + id + " deleted successfully!";
        }
    }
}
