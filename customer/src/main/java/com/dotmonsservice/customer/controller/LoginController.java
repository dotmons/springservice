package com.dotmonsservice.customer.controller;


import com.dotmonsservice.customer.dto.UserDetailsDto;
import com.dotmonsservice.customer.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
       return authenticationService.getUserLoginDetails(request);
    }

    @PostMapping("/registerUser")
    public UserDetailsDto registerUser(@RequestBody UserDetailsDto userDetailsDto){
        return authenticationService.registerUserDetails(userDetailsDto);
    }
}
