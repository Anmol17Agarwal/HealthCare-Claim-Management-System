package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.ReviewLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLogRepository extends JpaRepository<ReviewLogEntity,Long> {
}
