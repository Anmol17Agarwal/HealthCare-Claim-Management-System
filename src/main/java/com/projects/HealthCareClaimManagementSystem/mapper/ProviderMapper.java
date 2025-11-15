package com.projects.HealthCareClaimManagementSystem.mapper;

import com.projects.HealthCareClaimManagementSystem.dto.ProviderDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.ProviderEntity;

public class ProviderMapper {

    public static ProviderEntity toEntity(ProviderDto providerDto){
        return ProviderEntity.builder()
                .providerId(providerDto.getProviderId())
                .providerName(providerDto.getProviderName())
                .npiNumber(providerDto.getNpiNumber())
                .specialty(providerDto.getSpecialty())
                .email(providerDto.getEmail())
                .contactNumber(providerDto.getContactNumber())
                .address(providerDto.getAddress())
                .state(providerDto.getState())
                .city(providerDto.getCity())
                .zipCode(providerDto.getZipCode())
                .createdAt(providerDto.getCreatedAt())
                .build();
    }

    public static ProviderDto toDto(ProviderEntity providerEntity){
        return ProviderDto.builder()
                .providerId(providerEntity.getProviderId())
                .providerName(providerEntity.getProviderName())
                .npiNumber(providerEntity.getNpiNumber())
                .specialty(providerEntity.getSpecialty())
                .email(providerEntity.getEmail())
                .contactNumber(providerEntity.getContactNumber())
                .address(providerEntity.getAddress())
                .state(providerEntity.getState())
                .city(providerEntity.getCity())
                .zipCode(providerEntity.getZipCode())
                .createdAt(providerEntity.getCreatedAt())
                .build();
    }
}
