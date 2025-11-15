package com.projects.HealthCareClaimManagementSystem.service;

import com.projects.HealthCareClaimManagementSystem.dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.*;
import com.projects.HealthCareClaimManagementSystem.exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.mapper.ClaimMapper;
import com.projects.HealthCareClaimManagementSystem.repository.ClaimRepository;
import com.projects.HealthCareClaimManagementSystem.repository.PatientRepository;
import com.projects.HealthCareClaimManagementSystem.repository.ProviderRepository;
import com.projects.HealthCareClaimManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    ClaimRepository claimRepository;

    public ClaimDto createClaim(ClaimEntity claimEntity) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity creater = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found " + username));
            PatientEntity patient = patientRepository.findById(claimEntity.getPatient().getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
            ProviderEntity provider = providerRepository.findById(claimEntity.getProvider().getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
            claimEntity.setClaimNumber(generateClaimNumber());
            claimEntity.setCreatedBy(creater);
            claimEntity.setPatient(patient);
            claimEntity.setProvider(provider);
            claimEntity.setStatus(ClaimStatus.SUBMITTED);
            claimEntity.setCreatedAt(LocalDateTime.now());

            ClaimEntity savedClaim = claimRepository.save(claimEntity);
            return ClaimMapper.toDto(savedClaim);
        } catch (Exception e) {
            throw new CustomException("Failed to create requested claim " + e.getMessage());
        }
    }

    public ClaimDto getClaimById(Long claimId) {
        ClaimEntity claimEntity = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + claimId));
        return ClaimMapper.toDto(claimEntity);
    }

    public List<ClaimDto> getAllClaims() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        List<ClaimEntity> claims;

        boolean isAdmin = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        boolean isAuditor = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_AUDITOR"));
        boolean isCoder = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_CODER"));
        boolean isProvider = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_PROVIDER"));

        if (isAdmin || isAuditor) {
            // Admin and Auditor can see all claims
            claims = claimRepository.findAll();
        } else if (isCoder || isProvider) {
            // Coder and Provider can see only their own claims
            claims = claimRepository.findByCreatedBy_UserId(user.getUserId());
        } else {
            throw new CustomException("Access denied: Unauthorized role");
        }

        return claims.stream()
                .map(ClaimMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ClaimDto updateClaim(Long claimId, ClaimEntity claimEntity) {
        try {
            ClaimEntity claim = claimRepository.findById(claimId)
                    .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

            if (claimEntity.getBilledAmount() != null)
                claim.setBilledAmount(claimEntity.getBilledAmount());
            if (claimEntity.getApprovedAmount() != null)
                claim.setApprovedAmount(claimEntity.getApprovedAmount());
            if (claimEntity.getDiagnosisCode() != null)
                claim.setDiagnosisCode(claimEntity.getDiagnosisCode());
            if (claimEntity.getProcedureCode() != null)
                claim.setProcedureCode(claimEntity.getProcedureCode());
            if (claimEntity.getStatus() != null)
                claim.setStatus(claimEntity.getStatus());

            claim.setUpdatedAt(LocalDateTime.now());

            ClaimEntity updated = claimRepository.save(claim);
            return ClaimMapper.toDto(updated);
        } catch (Exception e) {
            throw new CustomException("Failed to update claim: " + e.getMessage());
        }
    }


    @Transactional
    public void deleteClaim(Long claimId) {
        ClaimEntity claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        claimRepository.delete(claim);
    }

    private String generateClaimNumber() {
        return "CLM-" + System.currentTimeMillis();
    }
}
