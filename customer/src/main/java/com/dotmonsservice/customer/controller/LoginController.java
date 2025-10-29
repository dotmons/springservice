package com.dotmonsservice.customer.controller;


import com.dotmonsservice.customer.dto.UserDetailsDto;
import com.dotmonsservice.customer.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/customers/auth")
public class LoginController {

    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/userLogin")
    public UserDetailsDto authenticateUser(HttpServletRequest request){
        UserDetailsDto user = authenticationService.getUserLoginDetails(request);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    @PostMapping("/registerUser")
    public UserDetailsDto registerUser(@RequestBody UserDetailsDto userDetailsDto){
        return authenticationService.registerUserDetails(userDetailsDto);
    }
}
