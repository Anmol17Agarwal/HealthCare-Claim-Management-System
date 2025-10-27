package com.projects.HealthCareClaimManagementSystem.Entitiy;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "claim_id")
    private ClaimEntity claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private UserEntity reviewer;

    @Column(name = "action",nullable = false, length = 50)
    private String action; // SUBMITTED, APPROVED, REJECTED

    @Column(name = "remark",columnDefinition = "text")
    private String remarks;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}