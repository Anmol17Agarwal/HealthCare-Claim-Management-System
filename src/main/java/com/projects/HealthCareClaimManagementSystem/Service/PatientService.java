package com.projects.HealthCareClaimManagementSystem.Service;

import com.projects.HealthCareClaimManagementSystem.Dto.PatientDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.PatientEntity;
import com.projects.HealthCareClaimManagementSystem.Exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.Exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.Mapper.PatientMapper;
import com.projects.HealthCareClaimManagementSystem.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public PatientDto createPatient(PatientEntity patientEntity) {
        try {
            if (patientRepository.existsByInsuranceNumber(patientEntity.getInsuranceNumber())) {
                throw new ValidationException("Patient with insurance number already exists: " + patientEntity.getInsuranceNumber());
            }
            if (patientEntity.getEmail() != null &&
                    !patientEntity.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new ValidationException("Invalid email format: " + patientEntity.getEmail());
            }
            if (patientEntity.getPolicyExpiryDate() != null &&
                    patientEntity.getPolicyExpiryDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Policy expiry date cannot be in the past.");
            }
            patientEntity.setCreatedAt(LocalDateTime.now());

            PatientEntity savedPatient = patientRepository.save(patientEntity);
            return PatientMapper.toDto(savedPatient);
        } catch (Exception e) {
            throw new CustomException("Failed to create Patient Details: " + e.getMessage());
        }
    }

    public PatientDto getPatient(Long id) {
        PatientEntity patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
        return PatientMapper.toDto(patient);
    }

    public List<PatientDto> getAllPatients() {
        List<PatientEntity> patientDtos = patientRepository.findAll();
        return patientDtos
                .stream()
                .map(PatientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientDto updatePatients(Long id, PatientEntity patientEntity) {
        try {
            PatientEntity patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

            if (patientEntity.getPatientGender() != null) {
                patient.setPatientGender(patientEntity.getPatientGender());
            }
            if (patientEntity.getPatientName() != null) {
                patient.setPatientName(patientEntity.getPatientName());
            }
            if (patientEntity.getDob() != null) {
                patient.setDob(patientEntity.getDob());
            }
            if (patientEntity.getContactNumber() != null) {
                patient.setContactNumber(patient.getContactNumber());
            }
            if (patientEntity.getInsuranceNumber() != null) {
                if (patientRepository.existsByInsuranceNumberAndPatientIdNot(patientEntity.getInsuranceNumber(), id)) {
                    throw new CustomException("Another patient already has this insurance number.");
                }
                patient.setInsuranceNumber(patient.getInsuranceNumber());
            }
            if (patientEntity.getInsuredAmount() != null) {
                patient.setInsuredAmount(patient.getInsuredAmount());
            }
            if (patientEntity.getPolicyExpiryDate() != null) {
                patient.setPolicyExpiryDate(patient.getPolicyExpiryDate());
            }
            patientEntity.setUpdatedAt(LocalDateTime.now());
            PatientEntity updatedPatient = patientRepository.save(patient);
            return PatientMapper.toDto(updatedPatient);
        } catch (Exception e) {
            throw new CustomException("Failed to update Patient " + e.getMessage());
        }
    }

    @Transactional
    public void deletePatient(Long id) {
        PatientEntity patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

        patientRepository.delete(patient);
    }
}
