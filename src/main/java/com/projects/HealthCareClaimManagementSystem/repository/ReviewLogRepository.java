package com.projects.HealthCareClaimManagementSystem.repository;

import com.projects.HealthCareClaimManagementSystem.entitiy.ReviewLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLogRepository extends JpaRepository<ReviewLogEntity,Long> {
}
