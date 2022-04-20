package com.lageraho.springsecurity.init;


import com.lageraho.springsecurity.model.User;
import com.lageraho.springsecurity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Order(1)
@Component
public class UserInitializer implements ApplicationRunner {


    @Value("${umanagement.auth.default-user-username}")
    String defaultUsername;

    @Value("${umanagement.auth.default-user-password}")
    String defaultUserPassword;

    @Value("${umanagement.auth.default-user-name}")
    String defaultName;

    @Value("${umanagement.auth.default-user-email}")
    String defaultUserEmail;



    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count() == 0){
            User superUser = new User();

            superUser.setUsername(defaultUsername);
            superUser.setName(defaultName);
            superUser.setPassword(passwordEncoder.encode(defaultUserPassword));
            superUser.setEmail(defaultUserEmail);

            userRepository.save(superUser);

            log.debug(" SuperUser initialised user:{}, password: {}", superUser.getUsername(), superUser.getPassword());

        }
    }
}
