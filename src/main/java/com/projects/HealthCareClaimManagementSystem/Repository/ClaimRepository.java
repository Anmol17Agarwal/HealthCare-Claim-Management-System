package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {
    List<ClaimEntity> findByCreatedBy_UserId(Long userId);

}
