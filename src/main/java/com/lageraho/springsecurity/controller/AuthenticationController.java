package com.lageraho.springsecurity.controller;

import com.lageraho.springsecurity.model.AuthenticationRequest;
import com.lageraho.springsecurity.model.AuthenticationResponse;
import com.lageraho.springsecurity.service.UserDetailsServiceImpl;
import com.lageraho.springsecurity.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;


@RequestMapping("/api/authenticate")
@RestController
@Slf4j
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailService;
    @Autowired
    private JwtUtil jwtUtil;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @PostMapping(value = "")
    public ResponseEntity<?> createAuthenticationToken (@RequestBody @Valid AuthenticationRequest authenticationRequest,
                                                        BindingResult result) {
        log.info("Username :{}, password:{}", authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        if (result.hasErrors()) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = (Validator) validatorFactory.getValidator();
            Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(authenticationRequest);
            throw new ConstraintViolationException(violations);
        }
        try {

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
            } catch (BadCredentialsException e) {
//                timeoutManager.registerIncorrectTry(authenticationRequest.getUsername());
                throw new BadCredentialsException("Incorrect username or password", e);
            }
            final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            final String username = authenticationRequest.getUsername();
            log.debug("Generated new Token");

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserName(username);

            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
//            errorMap.put("error", localeMessageProvider.getMessage(AUTHENTICATION_BAD_CREDENTIALS));
//            return new ResponseEntity<>(errorMap, HttpStatus.UNAUTHORIZED);
            throw new BadCredentialsException("Incorrect username or password", e);
        }
    }


}
