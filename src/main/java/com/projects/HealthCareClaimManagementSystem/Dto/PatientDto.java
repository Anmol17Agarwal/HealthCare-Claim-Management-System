package com.projects.HealthCareClaimManagementSystem.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("patient_name")
    private String patientName;

    @JsonProperty("patient_gender")
    private String patientGender;

    @JsonProperty("dob")
    private LocalDateTime dob;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("insurance_number")
    private String insuranceNumber;

    @JsonProperty("insured_amount")
    private BigDecimal insuredAmount;

    @JsonProperty("policy_expiry_date")
    private LocalDateTime policyExpiryDate;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
