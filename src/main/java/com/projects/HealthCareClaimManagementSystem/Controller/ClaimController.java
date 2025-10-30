package com.projects.HealthCareClaimManagementSystem.Controller;

import com.projects.HealthCareClaimManagementSystem.Dto.ClaimDto;
import com.projects.HealthCareClaimManagementSystem.Service.ClaimService;
import com.projects.HealthCareClaimManagementSystem.Utility.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    ClaimService claimService;

    @PreAuthorize("hasAnyRole('ADMIN','CODER')")
    @PostMapping
    private ResponseEntity<CustomResponse<ClaimDto>> createClaim(@RequestBody ClaimDto claimDto){
         ClaimDto claim =   claimService.createClaim(claimDto);
         return ResponseEntity.ok(CustomResponse.success("Claim has been created successfully",claim));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CODER','PROVIDER','AUDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<ClaimDto>> getClaimById(@PathVariable Long id){
            ClaimDto claim = claimService.getClaimById(id);
            return ResponseEntity.ok(CustomResponse.success("Claim retrieved successfully",claim));
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<ClaimDto>>> getAllClaims(){
            List<ClaimDto> claimDtos = claimService.getAllClaims();
            return ResponseEntity.ok(CustomResponse.success("Claims retrieved successfully",claimDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<ClaimDto>> updateClaim(@PathVariable Long id, @RequestBody ClaimDto claimDto){
            ClaimDto updatedClaim = claimService.updateClaim(id,claimDto);
            return ResponseEntity.ok(CustomResponse.success("Claim updated Successfully",updatedClaim));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteClaim(@PathVariable Long id){
            claimService.deleteClaim(id);
            return ResponseEntity.ok(CustomResponse.success("Claim Deleted with ID: "+id,null));
    }
}
