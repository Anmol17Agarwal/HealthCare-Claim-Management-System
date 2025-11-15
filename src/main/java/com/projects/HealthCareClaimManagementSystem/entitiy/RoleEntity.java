package com.projects.HealthCareClaimManagementSystem.entitiy;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name; // e.g., ROLE_ADMIN, ROLE_CODER, ROLE_REVIEWER, ROLE_AUDITOR

    public String getName() {
        return name;
    }

}
