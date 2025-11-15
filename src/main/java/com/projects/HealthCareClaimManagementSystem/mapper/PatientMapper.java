package com.projects.HealthCareClaimManagementSystem.mapper;

import com.projects.HealthCareClaimManagementSystem.dto.PatientDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.PatientEntity;

public class PatientMapper {

    public static PatientEntity toEntity(PatientDto patientDto){
        return PatientEntity.builder()
                .patientId(patientDto.getPatientId())
                .patientName(patientDto.getPatientName())
                .email(patientDto.getEmail())
                .contactNumber(patientDto.getContactNumber())
                .address(patientDto.getAddress())
                .patientGender(patientDto.getPatientGender())
                .dob(patientDto.getDob())
                .insuranceNumber(patientDto.getInsuranceNumber())
                .insuredAmount(patientDto.getInsuredAmount())
                .policyExpiryDate(patientDto.getPolicyExpiryDate())
                .createdAt(patientDto.getCreatedAt())
                .build();
    }

    public static PatientDto toDto(PatientEntity patientEntity){
        return PatientDto.builder()
                .patientId(patientEntity.getPatientId())
                .patientName(patientEntity.getPatientName())
                .email(patientEntity.getEmail())
                .contactNumber(patientEntity.getContactNumber())
                .address(patientEntity.getAddress())
                .patientGender(patientEntity.getPatientGender())
                .dob(patientEntity.getDob())
                .insuranceNumber(patientEntity.getInsuranceNumber())
                .insuredAmount(patientEntity.getInsuredAmount())
                .policyExpiryDate(patientEntity.getPolicyExpiryDate())
                .createdAt(patientEntity.getCreatedAt())
                .build();
    }
}
