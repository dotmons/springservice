package com.dotmonsservice.customer.service;


import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.dto.UserDetailsDto;
import com.dotmonsservice.customer.model.UserLogin;
import com.dotmonsservice.customer.repository.UserLoginRepository;
import com.dotmonsservice.customer.util.StatusEnum;
import com.dotmonsservice.customer.util.TokenGenerator;
import com.dotmonsservice.customer.util.UserRoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class AuthenticationService {

    private UserDetailsDto userDetailsDto;
    private UserLoginRepository userLoginRepository;

    public AuthenticationService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public UserDetailsDto getUserLoginDetails(HttpServletRequest request) {

        if ((request.getHeader("Authorization") != null) && request.getHeader("Authorization").equals("valid-token")) {
            userDetailsDto = UserDetailsDto.builder().username(request.getParameter("username"))
                    .role("Admin").build();
        } else {
            log.info("Authorization header is empty");
        }
        return userDetailsDto;
    }

    @Transactional
    public UserDetailsDto registerUserDetails(UserDetailsDto userDetailsDto) {

        UserLogin userLogin = UserLogin.builder().username(userDetailsDto.getUsername())
                .password(TokenGenerator.passwordEncoder(userDetailsDto.getPassword()))
                .token(TokenGenerator.generateToken())
                .userrole(UserRoleEnum.fromUserType(userDetailsDto.getRole()).toString())
                .build();
        UserLogin result = userLoginRepository.save(userLogin);

        return UserDetailsDto.builder().username(result.getUsername())
                .token(result.getToken()).status(StatusEnum.REGISTERED.getStatus()).build();
    }

    @Override
    public String toString() {
        return "AuthenticationService{" +
                "userDetailsDto=" + userDetailsDto.getUsername() + " Role: " + userDetailsDto.getRole() +
                '}';
    }
}
