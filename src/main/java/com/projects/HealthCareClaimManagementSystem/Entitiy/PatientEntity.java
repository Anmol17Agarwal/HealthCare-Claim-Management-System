package com.projects.HealthCareClaimManagementSystem.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private long patientId;

    @Column(name = "patient_name", nullable = false)
    @NotBlank(message = "patient name is required")
    private String patientName;

    @Column(name = "patient_gender", nullable = false)
    @NotBlank(message = "patient gender is required")
    private String patientGender;

    @Column(name = "dob", nullable = false)
    @NotBlank(message = "patient date of birth is required")
    private LocalDateTime dob;

    @Column(name = "contact_number", nullable = false,unique = true)
    @NotBlank(message = "Contact Number is required")
    private String contactNumber;

    @Column(name = "email", nullable = false,unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "address")
    @NotBlank(message = "Residential Address is required")
    private String address;

    @Column(name = "insurance_number", nullable = false,unique = true)
    @NotBlank(message = "Insurance number is required")
    private String insuranceNumber;

    @Column(name = "insured_amount", nullable = false)
    @NotNull(message = "Insured amount is required")
    private BigDecimal insuredAmount;

    @Column(name = "policy_expiry_date")
    private LocalDateTime policyExpiryDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
