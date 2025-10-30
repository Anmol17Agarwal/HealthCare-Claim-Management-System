package com.projects.HealthCareClaimManagementSystem.Mapper;

import com.projects.HealthCareClaimManagementSystem.Dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimEntity;

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
                .createdAt(claimDto.getCreatedAt())
                .updatedAt(claimDto.getUpdatedAt())
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
                .createdAt(claimEntity.getCreatedAt())
                .updatedAt(claimEntity.getUpdatedAt())
                .build();
    }
}
