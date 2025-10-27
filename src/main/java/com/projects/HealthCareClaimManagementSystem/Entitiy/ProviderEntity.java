package com.projects.HealthCareClaimManagementSystem.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "provider")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private long provider_id;

    @Column(name = "provider_name",nullable = false)
    private String providerName;


    @Column(name = "npi_number")
    private String npiNumber;


    @Column(name = "address",columnDefinition = "text")
    private String address;


    @CreationTimestamp
    private LocalDateTime createdAt;
}
