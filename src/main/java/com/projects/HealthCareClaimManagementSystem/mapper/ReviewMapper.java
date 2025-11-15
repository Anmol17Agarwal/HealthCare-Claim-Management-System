package com.projects.HealthCareClaimManagementSystem.mapper;

import com.projects.HealthCareClaimManagementSystem.dto.ReviewLogDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.ReviewLogEntity;

public class ReviewMapper {

    public static ReviewLogEntity toEntity(ReviewLogDto reviewLogDto){
        return ReviewLogEntity.builder()
                .id(reviewLogDto.getId())
                .claim(ClaimMapper.toEntity(reviewLogDto.getClaim()))
                .reviewer(UserMapper.toEntity(reviewLogDto.getReviewer()))
                .action(reviewLogDto.getAction())
                .remarks(reviewLogDto.getRemarks())
                .createdAt(reviewLogDto.getCreatedAt())
                .build();
    }

    public static ReviewLogDto toDto(ReviewLogEntity reviewLogEntity){
        return ReviewLogDto.builder()
                .id(reviewLogEntity.getId())
                .claim(ClaimMapper.toDto(reviewLogEntity.getClaim()))
                .reviewer(UserMapper.toDTO(reviewLogEntity.getReviewer()))
                .action(reviewLogEntity.getAction())
                .remarks(reviewLogEntity.getRemarks())
                .createdAt(reviewLogEntity.getCreatedAt())
                .build();
    }
}
