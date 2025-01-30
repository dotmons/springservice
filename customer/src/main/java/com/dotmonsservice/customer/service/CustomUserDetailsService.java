package com.dotmonsservice.customer.service;

import com.dotmonsservice.customer.repository.UserLoginRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserLoginRepository userLoginRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Loading user {}", username);
        String role = userLoginRepository.findRoleByUsername(username);

        log.info("Found role {}", role);

        return User.builder()
                .username(username)
                .password("")
                .authorities(role)
                .build();
    }
}
