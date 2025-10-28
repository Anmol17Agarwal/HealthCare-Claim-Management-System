package com.projects.HealthCareClaimManagementSystem.Config;

import com.projects.HealthCareClaimManagementSystem.Entitiy.RoleEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserRoles;
import com.projects.HealthCareClaimManagementSystem.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        Arrays.stream(UserRoles.values()).forEach(roleType -> {
            roleRepository.findByName(roleType.name())
                    .orElseGet(() -> roleRepository.save(new RoleEntity(null, roleType.name())));
        });
    }
}

