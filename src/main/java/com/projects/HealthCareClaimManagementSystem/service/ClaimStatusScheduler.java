package com.projects.HealthCareClaimManagementSystem.service;

import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimEntity;
import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimStatus;
import com.projects.HealthCareClaimManagementSystem.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ClaimStatusScheduler {

    @Autowired
    private ClaimRepository claimRepository;
    // Runs every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void updateSubmittedToPending() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);
        List<ClaimEntity> submittedClaims = claimRepository.findByStatusAndCreatedAtBefore(ClaimStatus.SUBMITTED, threshold);

        for (ClaimEntity claim : submittedClaims) {
            claim.setStatus(ClaimStatus.PENDING);
            claim.setUpdatedBy("SYSTEM");
            claim.setUpdatedAt(LocalDateTime.now());
            claimRepository.save(claim);
        }
    }
}
