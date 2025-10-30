package com.projects.HealthCareClaimManagementSystem.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDto {

    @JsonProperty("provider_id")
    private Long providerId;

    @JsonProperty("provider_name")
    private String providerName;

    @JsonProperty("npi_number")
    private String npiNumber;

    @JsonProperty("specialty")
    private String specialty;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
