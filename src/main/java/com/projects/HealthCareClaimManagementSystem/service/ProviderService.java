package com.projects.HealthCareClaimManagementSystem.service;

import com.projects.HealthCareClaimManagementSystem.dto.ProviderDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.ProviderEntity;
import com.projects.HealthCareClaimManagementSystem.exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.mapper.ProviderMapper;
import com.projects.HealthCareClaimManagementSystem.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    public ProviderDto createProvider(ProviderEntity providerEntity) {
        try {
            if (providerRepository.existsByNpiNumber(providerEntity.getNpiNumber())) {
                throw new ValidationException("Provider with npi number already exists: " + providerEntity.getNpiNumber());
            }
            if (providerRepository.existsByContactNumber(providerEntity.getContactNumber())) {
                throw new ValidationException("Provider with contact number already exists: " + providerEntity.getContactNumber());
            }
            if (providerRepository.existsByEmail(providerEntity.getEmail())) {
                throw new ValidationException("provider with email id already exists: " + providerEntity.getEmail());
            }
            providerEntity.setCreatedAt(LocalDateTime.now());
            ProviderEntity savedProvider = providerRepository.save(providerEntity);
            return ProviderMapper.toDto(savedProvider);
        } catch (Exception e) {
            throw new CustomException("Failed to create Provider Details "+e.getMessage());
        }
    }

    public ProviderDto getProviderById(Long id){
        ProviderEntity provider = providerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Provider not found with ID: "+id));
        return ProviderMapper.toDto(provider);
    }

    public List<ProviderDto> getAllProviders(){
        List<ProviderEntity> providerEntityList = providerRepository.findAll();
        return providerEntityList.stream()
                .map(ProviderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProviderDto updateProvider(Long id, ProviderEntity providerEntity){
        try{
            ProviderEntity provider = providerRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Provider not found with ID: "+id));

            if(providerEntity.getProviderName()!=null){
                provider.setProviderName(providerEntity.getProviderName());
            }if(providerEntity.getNpiNumber()!=null){
                if(providerRepository.existsByNpiNumberAndProviderIdNot(providerEntity.getNpiNumber(),id)){
                    throw new CustomException("Another provider already has this npi number.");
                }else{
                    provider.setNpiNumber(providerEntity.getNpiNumber());
                }
            }if(providerEntity.getSpecialty()!=null){
                provider.setSpecialty(providerEntity.getSpecialty());
            }if(providerEntity.getContactNumber()!=null){
                if(providerRepository.existsByContactNumberAndProviderIdNot(providerEntity.getContactNumber(),id)){
                    throw new CustomException("Another provider already has this contact number.");
                }else{
                    provider.setContactNumber(providerEntity.getContactNumber());
                }
            }if(providerEntity.getEmail()!=null){
                if(providerRepository.existsByEmailAndProviderIdNot(providerEntity.getEmail(),id)){
                    throw new CustomException("Another provider already has this email id.");
                }
            }if(providerEntity.getAddress()!=null){
                provider.setAddress(providerEntity.getAddress());
            }if(providerEntity.getCity()!=null){
                provider.setCity(provider.getCity());
            }if(providerEntity.getState()!=null){
                provider.setState(providerEntity.getState());
            }if(providerEntity.getZipCode()!=null){
                provider.setZipCode(providerEntity.getZipCode());
            }
            provider.setUpdatedAt(LocalDateTime.now());
            ProviderEntity updatedProvider = providerRepository.save(provider);
            return ProviderMapper.toDto(updatedProvider);
        }catch (Exception e){
            throw new CustomException("Failed to update Provider Details: "+e.getMessage());
        }
    }

    @Transactional
    public void deleteProvider(Long id){

        ProviderEntity provider = providerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Provider not found with ID: "+id));

        providerRepository.delete(provider);
    }
}
