package com.projects.HealthCareClaimManagementSystem.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimDto {

    @JsonProperty("claim_id")
    private Long claimId;

    @JsonProperty("claim_number")
    private String claimNumber;

    @JsonProperty("patient_id")
    private PatientDto patient;

    @JsonProperty("provider_id")
    private ProviderDto provider;

    @JsonProperty("billed_amount")
    private BigDecimal billedAmount;

    @JsonProperty("approved_amount")
    private BigDecimal approvedAmount;

    @JsonProperty("diagnosis_code")
    private String diagnosisCode;

    @JsonProperty("procedure_code")
    private String procedureCode;

    @JsonProperty("status")
    private ClaimStatus status;

    @JsonProperty("admission_date")
    private LocalDateTime admissionDate;

    @JsonProperty("discharge_date")
    private LocalDateTime dischargeDate;

    @JsonProperty("created_by")
    private UserDto createdBy;

    @JsonProperty("reviewed_by")
    private UserDto reviewedBy;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("reviewed_at")
    private LocalDateTime reviewedAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}

