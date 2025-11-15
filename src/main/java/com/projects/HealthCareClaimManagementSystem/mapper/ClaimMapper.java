package com.projects.HealthCareClaimManagementSystem.mapper;

import com.projects.HealthCareClaimManagementSystem.dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimEntity;

public class ClaimMapper {

    public static ClaimEntity toEntity(ClaimDto claimDto){
        return ClaimEntity.builder()
                .claimId(claimDto.getClaimId())
                .claimNumber(claimDto.getClaimNumber())
                .patient(PatientMapper.toEntity(claimDto.getPatient()))
                .provider(ProviderMapper.toEntity(claimDto.getProvider()))
                .billedAmount(claimDto.getBilledAmount())
                .approvedAmount(claimDto.getApprovedAmount())
                .diagnosisCode(claimDto.getDiagnosisCode())
                .procedureCode(claimDto.getProcedureCode())
                .status(claimDto.getStatus())
                .admissionDate(claimDto.getAdmissionDate())
                .dischargeDate(claimDto.getDischargeDate())
                .createdBy(UserMapper.toEntity(claimDto.getCreatedBy()))
                .reviewedBy(UserMapper.toEntity(claimDto.getReviewedBy()))
                .updatedBy(claimDto.getUpdatedBy())
                .createdAt(claimDto.getCreatedAt())
                .updatedAt(claimDto.getUpdatedAt())
                .reviewedAt(claimDto.getReviewedAt())
                .build();
    }

    public static ClaimDto toDto(ClaimEntity claimEntity){
        return ClaimDto.builder()
                .claimId(claimEntity.getClaimId())
                .claimNumber(claimEntity.getClaimNumber())
                .patient(PatientMapper.toDto(claimEntity.getPatient()))
                .provider(ProviderMapper.toDto(claimEntity.getProvider()))
                .billedAmount(claimEntity.getBilledAmount())
                .approvedAmount(claimEntity.getApprovedAmount())
                .diagnosisCode(claimEntity.getDiagnosisCode())
                .procedureCode(claimEntity.getProcedureCode())
                .status(claimEntity.getStatus())
                .admissionDate(claimEntity.getAdmissionDate())
                .dischargeDate(claimEntity.getDischargeDate())
                .createdBy(UserMapper.toDTO(claimEntity.getCreatedBy()))
                .reviewedBy(UserMapper.toDTO(claimEntity.getReviewedBy()))
                .updatedBy(claimEntity.getUpdatedBy())
                .createdAt(claimEntity.getCreatedAt())
                .updatedAt(claimEntity.getUpdatedAt())
                .reviewedAt(claimEntity.getReviewedAt())
                .build();
    }
}
