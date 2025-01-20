package com.dotmonsservice.customer.service;


import com.dotmonsservice.customer.dto.CustomerDTO;
import com.dotmonsservice.customer.dto.UserDetailsDto;
import com.dotmonsservice.customer.model.UserLogin;
import com.dotmonsservice.customer.repository.UserLoginRepository;
import com.dotmonsservice.customer.security.JwtUtil;
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
    private final UserLoginRepository userLoginRepository;
    private final JwtUtil jwtUtil;


    public AuthenticationService(UserLoginRepository userLoginRepository, JwtUtil jwtUtil) {
        this.userLoginRepository = userLoginRepository;
        this.jwtUtil = jwtUtil;
    }

    public UserDetailsDto getUserLoginDetails(HttpServletRequest request) {

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        UserLogin userLogin = null;

        if ((!Objects.isNull(username) && (!Objects.isNull(password)))) {
            String encodedPassword = TokenGenerator.passwordEncoder(password);

            log.info("Username {} and password {}", username, encodedPassword);

            userLogin = userLoginRepository.findByUsernameAndPassword(username, encodedPassword);
            log.info("userLogin: {}", userLogin.getUsername());

            // To generate JWT Token
            if ((userLogin.getId() != null) && (userLogin.getPassword() != null) && userLogin.getPassword()
                    .equals(TokenGenerator.passwordEncoder(password))) {
                String token = jwtUtil.generateToken(userLogin.getUsername());
                log.info("token: {}", token);
                userDetailsDto = UserDetailsDto.builder().username(userLogin.getUsername())
                        .token(token).build();
            }
        }
        return userDetailsDto;
    }

    @Transactional
    public UserDetailsDto registerUserDetails(UserDetailsDto userDetailsDto) {

        UserLogin userLogin = UserLogin.builder().username(userDetailsDto.getUsername())
                .password(TokenGenerator.passwordEncoder(userDetailsDto.getPassword()))
                .userrole(UserRoleEnum.fromUserType(userDetailsDto.getRole()).toString())
                .build();
        UserLogin result = userLoginRepository.save(userLogin);

        return UserDetailsDto.builder().username(result.getUsername())
                .status(StatusEnum.REGISTERED.getStatus())
                .role(result.getUserrole()).build();
    }

    @Override
    public String toString() {
        return "AuthenticationService{" +
                "userDetailsDto=" + userDetailsDto.getUsername() + " Role: " + userDetailsDto.getRole() +
                '}';
    }
}
