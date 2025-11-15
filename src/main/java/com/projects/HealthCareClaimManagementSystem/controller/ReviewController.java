package com.projects.HealthCareClaimManagementSystem.controller;

import com.projects.HealthCareClaimManagementSystem.dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.service.ReviewService;
import com.projects.HealthCareClaimManagementSystem.utility.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/pending")
    public ResponseEntity<CustomResponse<List<ClaimDto>>> getPendingClaims(){
        List<ClaimDto> claimDto = reviewService.getPendingClaims();
        return ResponseEntity.ok(CustomResponse.success("Fetech all pending records. ",claimDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<CustomResponse<String>> approveClaim(@PathVariable Long id){
        reviewService.approveClaim(id);
        return ResponseEntity.ok(CustomResponse.success("Claim is approved successfully",null));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<CustomResponse<String>> rejectClaim(@PathVariable Long id){
        reviewService.rejectClaim(id);
        return ResponseEntity.ok(CustomResponse.success("Claim is rejected.",null));
    }
}
