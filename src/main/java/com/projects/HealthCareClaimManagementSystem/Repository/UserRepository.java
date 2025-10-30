package com.projects.HealthCareClaimManagementSystem.Repository;

import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByUserEmail(String email);
}
