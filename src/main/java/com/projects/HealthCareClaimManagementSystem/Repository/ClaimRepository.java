package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<ClaimEntity,Long> {

    List<ClaimEntity> findByCreatedBy_User_id(Long userId);
}
