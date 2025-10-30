package com.projects.HealthCareClaimManagementSystem.Controller;

import com.projects.HealthCareClaimManagementSystem.Dto.PatientDto;
import com.projects.HealthCareClaimManagementSystem.Service.PatientService;
import com.projects.HealthCareClaimManagementSystem.Utility.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CustomResponse<PatientDto>> createPatient(@Valid @RequestBody PatientDto patientDto){
        PatientDto patient = patientService.createPatient(patientDto);
        return ResponseEntity.ok(CustomResponse.success("Patient details created successfully",patient));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CODER','PROVIDER','AUDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<PatientDto>> getPatientById(@PathVariable Long id){
        PatientDto patient = patientService.getPatient(id);
        return ResponseEntity.ok(CustomResponse.success("Patient details retrieved by id "+id+" successfully",patient));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CODER','PROVIDER','AUDITOR')")
    @GetMapping
    public ResponseEntity<CustomResponse<List<PatientDto>>> getAllPatients(){
        List<PatientDto> patientDtoList = patientService.getAllPatients();
        return ResponseEntity.ok(CustomResponse.success("All existing Patient Details retrieved successfully",patientDtoList));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<PatientDto>> updatePatient(@PathVariable Long id,@Valid @RequestBody PatientDto patientDto){
        PatientDto updatedPatient = patientService.updatePatients(id,patientDto);
        return ResponseEntity.ok(CustomResponse.success("Patient Details updated Successfully",updatedPatient));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.ok(CustomResponse.success("Patient with id: "+id+" deleted successfully",null));
    }
}
