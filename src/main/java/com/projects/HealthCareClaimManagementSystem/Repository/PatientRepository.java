package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {
    boolean existsByInsuranceNumber(String insuranceNumber);
    boolean existsByInsuranceNumberAndPatientIdNot(String insuranceNumber, Long patientId);

}
