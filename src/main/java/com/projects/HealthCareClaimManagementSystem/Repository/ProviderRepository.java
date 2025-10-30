package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<ProviderEntity,Long> {
}
