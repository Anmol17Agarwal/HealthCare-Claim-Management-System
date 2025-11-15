package com.projects.HealthCareClaimManagementSystem.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private long claimId;

    @Column(name = "claim_number", unique = true, nullable = false)
    private String claimNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id")
    private ProviderEntity provider;

    @Column(name = "billed_amount",nullable = false, precision = 12, scale = 2)
    private BigDecimal billedAmount;

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

    @Column(name = "diagnosis_code")
    private String diagnosisCode;

    @Column(name = "procedure_code")
    private String procedureCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false, length = 30)
    private ClaimStatus status; // SUBMITTED, APPROVED, REJECTED

    @Column(name = "admission_date",nullable = false)
    @NotNull(message = "Admission date is required")
    private LocalDateTime admissionDate;

    @Column(name = "discharge_date",nullable = false)
    @NotNull(message = "Discharge date is required")
    private LocalDateTime dischargeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @JoinColumn(name = "updated_by")
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private UserEntity reviewedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}