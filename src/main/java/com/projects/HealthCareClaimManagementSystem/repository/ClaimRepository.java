package com.projects.HealthCareClaimManagementSystem.repository;

import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimEntity;
import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
    List<ClaimEntity> findByCreatedBy_UserId(Long userId);
    List<ClaimEntity> findByStatus(ClaimStatus status);
    List<ClaimEntity> findByStatusAndCreatedAtBefore(ClaimStatus status, LocalDateTime createdAt);

}
