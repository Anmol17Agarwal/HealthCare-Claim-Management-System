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

    public PatientDto createPatient(PatientDto patientDto) {
        try {
            if (patientRepository.existsByInsuranceNumber(patientDto.getInsuranceNumber())) {
                throw new ValidationException("Patient with insurance number already exists: " + patientDto.getInsuranceNumber());
            }
            if (patientDto.getEmail() != null &&
                    !patientDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new ValidationException("Invalid email format: " + patientDto.getEmail());
            }
            if (patientDto.getPolicyExpiryDate() != null &&
                    patientDto.getPolicyExpiryDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Policy expiry date cannot be in the past.");
            }
            PatientEntity patient = PatientMapper.toEntity(patientDto);
            patient.setCreatedAt(LocalDateTime.now());

            PatientEntity savedPatient = patientRepository.save(patient);
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
    public PatientDto updatePatients(Long id, PatientDto patientDto) {
        try {
            PatientEntity patient = patientRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));

            if (patientDto.getPatientGender() != null) {
                patient.setPatientGender(patientDto.getPatientGender());
            }
            if (patientDto.getPatientName() != null) {
                patient.setPatientName(patientDto.getPatientName());
            }
            if (patientDto.getDob() != null) {
                patient.setDob(patientDto.getDob());
            }
            if (patientDto.getContactNumber() != null) {
                patient.setContactNumber(patient.getContactNumber());
            }
            if (patientDto.getInsuranceNumber() != null) {
                if (patientRepository.existsByInsuranceNumberAndPatientIdNot(patientDto.getInsuranceNumber(), id)) {
                    throw new CustomException("Another patient already has this insurance number.");
                }
                patient.setInsuranceNumber(patient.getInsuranceNumber());
            }
            if (patientDto.getInsuredAmount() != null) {
                patient.setInsuredAmount(patient.getInsuredAmount());
            }
            if (patientDto.getPolicyExpiryDate() != null) {
                patient.setPolicyExpiryDate(patient.getPolicyExpiryDate());
            }
            patientDto.setUpdatedAt(LocalDateTime.now());
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
