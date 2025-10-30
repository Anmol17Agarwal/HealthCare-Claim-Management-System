package com.projects.HealthCareClaimManagementSystem.Service;

import com.projects.HealthCareClaimManagementSystem.Dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.*;
import com.projects.HealthCareClaimManagementSystem.Exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.Exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.Mapper.ClaimMapper;
import com.projects.HealthCareClaimManagementSystem.Repository.ClaimRepository;
import com.projects.HealthCareClaimManagementSystem.Repository.PatientRepository;
import com.projects.HealthCareClaimManagementSystem.Repository.ProviderRepository;
import com.projects.HealthCareClaimManagementSystem.Repository.UserRepository;
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

    public ClaimDto createClaim(ClaimDto claimDto) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity creater = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found " + username));
            PatientEntity patient = patientRepository.findById(claimDto.getPatient().getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
            ProviderEntity provider = providerRepository.findById(claimDto.getProvider().getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));

            ClaimEntity claim = ClaimMapper.toEntity(claimDto);
            claim.setClaimNumber(generateClaimNumber());
            claim.setCreatedBy(creater);
            claim.setPatient(patient);
            claim.setProvider(provider);
            claim.setStatus(ClaimStatus.SUBMITTED);
            claim.setCreatedAt(LocalDateTime.now());

            ClaimEntity savedClaim = claimRepository.save(claim);
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
    public ClaimDto updateClaim(Long claimId, ClaimDto claimDto) {
        try {
            ClaimEntity claim = claimRepository.findById(claimId)
                    .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

            if (claimDto.getBilledAmount() != null)
                claim.setBilledAmount(claimDto.getBilledAmount());
            if (claimDto.getApprovedAmount() != null)
                claim.setApprovedAmount(claimDto.getApprovedAmount());
            if (claimDto.getDiagnosisCode() != null)
                claim.setDiagnosisCode(claimDto.getDiagnosisCode());
            if (claimDto.getProcedureCode() != null)
                claim.setProcedureCode(claimDto.getProcedureCode());
            if (claimDto.getStatus() != null)
                claim.setStatus(claimDto.getStatus());

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
