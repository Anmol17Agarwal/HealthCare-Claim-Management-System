package com.projects.HealthCareClaimManagementSystem.Service;

import com.projects.HealthCareClaimManagementSystem.Dto.ProviderDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.ProviderEntity;
import com.projects.HealthCareClaimManagementSystem.Exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.Exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.Mapper.ProviderMapper;
import com.projects.HealthCareClaimManagementSystem.Repository.ProviderRepository;
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

    public ProviderDto createProvider(ProviderDto providerDto) {
        try {
            if (providerRepository.existsByNpiNumber(providerDto.getNpiNumber())) {
                throw new ValidationException("Provider with npi number already exists: " + providerDto.getNpiNumber());
            }
            if (providerRepository.existsByContactNumber(providerDto.getContactNumber())) {
                throw new ValidationException("Provider with contact number already exists: " + providerDto.getContactNumber());
            }
            if (providerRepository.existsByEmail(providerDto.getEmail())) {
                throw new ValidationException("provider with email id already exists: " + providerDto.getEmail());
            }
            ProviderEntity provider = ProviderMapper.toEntity(providerDto);
            provider.setCreatedAt(LocalDateTime.now());
            ProviderEntity savedProvider = providerRepository.save(provider);
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
    public ProviderDto updateProvider(Long id, ProviderDto providerDto){
        try{
            ProviderEntity provider = providerRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Provider not found with ID: "+id));

            if(providerDto.getProviderName()!=null){
                provider.setProviderName(providerDto.getProviderName());
            }if(providerDto.getNpiNumber()!=null){
                if(providerRepository.existsByNpiNumberAndProviderIdNot(providerDto.getNpiNumber(),id)){
                    throw new CustomException("Another provider already has this npi number.");
                }else{
                    provider.setNpiNumber(providerDto.getNpiNumber());
                }
            }if(providerDto.getSpecialty()!=null){
                provider.setSpecialty(providerDto.getSpecialty());
            }if(providerDto.getContactNumber()!=null){
                if(providerRepository.existsByContactNumberAndProviderIdNot(providerDto.getContactNumber(),id)){
                    throw new CustomException("Another provider already has this contact number.");
                }else{
                    provider.setContactNumber(providerDto.getContactNumber());
                }
            }if(providerDto.getEmail()!=null){
                if(providerRepository.existsByEmailAndProviderIdNot(providerDto.getEmail(),id)){
                    throw new CustomException("Another provider already has this email id.");
                }
            }if(providerDto.getAddress()!=null){
                provider.setAddress(providerDto.getAddress());
            }if(providerDto.getCity()!=null){
                provider.setCity(provider.getCity());
            }if(providerDto.getState()!=null){
                provider.setState(providerDto.getState());
            }if(providerDto.getZipCode()!=null){
                provider.setZipCode(providerDto.getZipCode());
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
