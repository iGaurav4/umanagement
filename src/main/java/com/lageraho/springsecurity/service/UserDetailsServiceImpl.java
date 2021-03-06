package com.lageraho.springsecurity.service;

import com.lageraho.springsecurity.model.User;
import com.lageraho.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if( user == null || user.getRole() == null){
            throw new UsernameNotFoundException("User/ Role doesn't exists, Please register");
        }
        return user;
    }
}
