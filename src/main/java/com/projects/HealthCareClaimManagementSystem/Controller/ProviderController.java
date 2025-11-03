package com.projects.HealthCareClaimManagementSystem.Controller;

import com.projects.HealthCareClaimManagementSystem.Dto.PatientDto;
import com.projects.HealthCareClaimManagementSystem.Dto.ProviderDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ProviderEntity;
import com.projects.HealthCareClaimManagementSystem.Service.ProviderService;
import com.projects.HealthCareClaimManagementSystem.Utility.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CustomResponse<ProviderDto>> createProvider(@Valid@RequestBody ProviderEntity providerEntity){
        ProviderDto provider = providerService.createProvider(providerEntity);
        return ResponseEntity.ok(CustomResponse.success("Provider Details created successfully",provider));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR','PROVIDER')")//need to add provider who can see his own details only
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<ProviderDto>> getProvider(@PathVariable Long id){
        ProviderDto provider = providerService.getProviderById(id);
        return ResponseEntity.ok(CustomResponse.success("Provider Details retrieved successfully with ID: "+id,provider));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<CustomResponse<List<ProviderDto>>> getAllProviders(){
        List<ProviderDto> providerDtosList = providerService.getAllProviders();
        return ResponseEntity.ok(CustomResponse.success("All existing Provider Details retrieved successfully.",providerDtosList));
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROVIDER')") //need to add provider also who can edit limit filed slike his change of address or new contact number or any othe rupdating
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<ProviderDto>> updateProvider(@PathVariable Long id, @Valid@RequestBody ProviderEntity providerEntity){
        ProviderDto provider = providerService.updateProvider(id,providerEntity);
        return ResponseEntity.ok(CustomResponse.success("Provider details updated successfully",provider));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteProvider(@PathVariable Long id){
        providerService.deleteProvider(id);
        return ResponseEntity.ok(CustomResponse.success("Provider details deleted successfully.",null));
    }
}
