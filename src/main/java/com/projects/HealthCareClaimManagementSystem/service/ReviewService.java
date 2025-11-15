package com.projects.HealthCareClaimManagementSystem.service;

import com.projects.HealthCareClaimManagementSystem.dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimEntity;
import com.projects.HealthCareClaimManagementSystem.entitiy.ClaimStatus;
import com.projects.HealthCareClaimManagementSystem.entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.mapper.ClaimMapper;
import com.projects.HealthCareClaimManagementSystem.repository.ClaimRepository;
import com.projects.HealthCareClaimManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ClaimDto> getPendingClaims(){
        List<ClaimEntity> claim = claimRepository.findByStatus(ClaimStatus.PENDING);
        return claim.stream().map(ClaimMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void approveClaim(Long id){
        try {
            ClaimEntity claim = claimRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Claim not found with ID: "+id));
            if (!claim.getStatus().equals(ClaimStatus.PENDING)) {
                throw new CustomException("Only pending claims can be approved");
            }

            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity user = userRepository.findByUsername(userName)
                    .orElseThrow(()->new ResourceNotFoundException("User is not listed"));
            claim.setStatus(ClaimStatus.APPROVED);
            claim.setReviewedBy(user);
            claim.setReviewedAt(LocalDateTime.now());

            claimRepository.save(claim);
        }catch (Exception e){
            throw new CustomException("Unable to approved claim: "+e.getMessage());
        }
    }

    @Transactional
    public void rejectClaim(Long id){
        try{
            ClaimEntity claim = claimRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Claim not found with ID: "+id));
            if(!claim.getStatus().equals(ClaimStatus.PENDING)){
                throw new CustomException("Only pending claims can be rejected");
            }
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity user = userRepository.findByUsername(userName)
                    .orElseThrow(()->new ResourceNotFoundException("User is not listed"));
            claim.setStatus(ClaimStatus.REJECTED);
            claim.setReviewedBy(user);
            claim.setReviewedAt(LocalDateTime.now());

            claimRepository.save(claim);
        }catch (Exception e){
            throw new CustomException("Unable to reject claim: "+e.getMessage());
        }
    }
}
