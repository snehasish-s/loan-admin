package com.sih.loan_admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sih.loan_admin.Model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}

