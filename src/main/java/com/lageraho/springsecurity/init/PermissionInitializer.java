package com.lageraho.springsecurity.init;


import com.lageraho.springsecurity.model.Permission;
import com.lageraho.springsecurity.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order (1)
public class PermissionInitializer implements ApplicationRunner {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!permissionRepository.findAll().iterator().hasNext()) {
            //User Management Permissions
            permissionRepository.save(Permission.builder()
                    .grantedAuthority(new SimpleGrantedAuthority("ROLE_R"))
                    .description("User is able to read User Management Information")
                    .type(Permission.PermissionType.Read)
                    .build());

            permissionRepository.save(Permission.builder()
                    .grantedAuthority(new SimpleGrantedAuthority("ROLE_RW"))
                    .description("User is able to read/write User Management Information")
                    .type(Permission.PermissionType.ReadWrite)
                    .build());


            permissionRepository.save(Permission.builder()
                    .grantedAuthority(new SimpleGrantedAuthority("ROLE_RE"))
                    .description("User is able to read/execute Configuration Information")
                    .type(Permission.PermissionType.ReadExecute)
                    .build());


            log.debug("Permissions initialized");
        } else {
            log.debug("Permissions were already initialized");
        }
    }
}
