package com.sih.loan_admin.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long beneficiaryId; // Link to Beneficiary
    private String type;        // APPROVE, REJECT, WALLET_TOPUP, etc.
    private Double amount;      // Money involved (if any)
    private LocalDateTime timestamp;

    // Media fields
    private String photoPath;   // Path to uploaded photo
    private String videoPath;   // Path to uploaded video
    private String metadata;    // JSON string for IP, location, device, etc.
    private String aiFraudStatus; // "SAFE" or "SUSPICIOUS"

    // Constructors
    public Transaction() {}

    public Transaction(Long beneficiaryId, String type, Double amount,
                       String photoPath, String videoPath, String metadata, String aiFraudStatus) {
        this.beneficiaryId = beneficiaryId;
        this.type = type;
        this.amount = amount;
        this.photoPath = photoPath;
        this.videoPath = videoPath;
        this.metadata = metadata;
        this.aiFraudStatus = aiFraudStatus;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(Long beneficiaryId) { this.beneficiaryId = beneficiaryId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
    public String getVideoPath() { return videoPath; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public String getAiFraudStatus() { return aiFraudStatus; }
    public void setAiFraudStatus(String aiFraudStatus) { this.aiFraudStatus = aiFraudStatus; }
}
