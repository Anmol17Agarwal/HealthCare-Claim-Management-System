package com.projects.HealthCareClaimManagementSystem.Entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "providers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private long providerId;

    @Column(name = "provider_name",nullable = false)
    private String providerName;

    @Column(name = "npi_number",unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "NPI Number must be exactly 10 digits")
    private String npiNumber;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "contact_number",unique = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    @Pattern(regexp = "^[0-9]{5,6}$", message = "Invalid ZIP code")
    private String zipCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
