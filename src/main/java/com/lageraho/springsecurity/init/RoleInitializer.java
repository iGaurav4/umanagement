package com.lageraho.springsecurity.init;


import com.lageraho.springsecurity.model.Permission;
import com.lageraho.springsecurity.model.Role;
import com.lageraho.springsecurity.repository.PermissionRepository;
import com.lageraho.springsecurity.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Slf4j
@Order (2)
public class RoleInitializer implements ApplicationRunner {


    @Value("${umanagement.auth.default-role-name}")
    String defaultSuperRoleName;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (roleRepository.count() == 0) {
            //Super role initialization
            HashSet<Permission> permissions = new HashSet<>(permissionRepository.findAll());
            roleRepository.save(Role.builder()
                    .name(defaultSuperRoleName)
                    .description("Role with all permissions")
                    .permissions(permissions).build());

            log.debug("Superuser role initialized");
        } else {
            log.debug("Default role was already initialized");
        }
    }
}
