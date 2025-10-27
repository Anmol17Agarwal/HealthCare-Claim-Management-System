package com.projects.HealthCareClaimManagementSystem.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paitent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private long patientId;

    @Column(name = "patient_name",nullable = false)
    @NotBlank(message = "patient name is required")
    private String patient_name;


    @Column(name = "dob",nullable = false)
    private LocalDate dob;


    @Column(name = "insurance_number",nullable = false)
    private String insuranceNumber;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
