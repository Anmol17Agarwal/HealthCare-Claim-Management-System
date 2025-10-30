package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity,Long> {
}
