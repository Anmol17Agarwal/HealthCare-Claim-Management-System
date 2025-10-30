package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity,Long> {
    boolean existsByNpiNumber(String npiNumber);
    boolean existsByContactNumber(String contactNumber);
    boolean existsByEmail(String email);
    boolean existsByNpiNumberAndProviderIdNot(String npiNumber, Long providerId);

    boolean existsByContactNumberAndProviderIdNot(String contactNumber,Long providerId);
    boolean existsByEmailAndProviderIdNot(String email, Long id);

}
