package com.projects.HealthCareClaimManagementSystem.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ClaimStatus;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewLogDto {
    @JsonProperty("review_id")
    private Long id;

    @JsonProperty("claim_id")
    private ClaimDto claim;

    @JsonProperty("reviewer_id")
    private UserDto reviewer;

    @JsonProperty("action")
    private ClaimStatus action; // SUBMITTED, APPROVED, REJECTED

    @JsonProperty("remark")
    private String remarks;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
