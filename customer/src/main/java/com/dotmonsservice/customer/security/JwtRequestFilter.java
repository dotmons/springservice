package com.dotmonsservice.customer.security;

import com.dotmonsservice.customer.repository.UserLoginRepository;
import com.dotmonsservice.customer.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       log.info("Filtering working...");
       try {

           // Skip JWT filter for actuator
           String path = request.getRequestURI();
           if (path.startsWith("/actuator")) {
               filterChain.doFilter(request, response);
               return;
           }


           final String authHeader = request.getHeader("Authorization");
           if (authHeader != null && authHeader.startsWith("Bearer ")) {
               final String token = authHeader.substring(7);
               log.info("token provided :  {} ", token);
               String username = jwtUtil.validateToken(token);
               if (Objects.nonNull(username)) {
                   log.info("Token is valid username: {}", username);

                   UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                   log.info("userDetails value : {}", userDetails);
                   // Create an authentication token
                   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                           userDetails, null, userDetails.getAuthorities()
                   );

                   // Set the authentication token in the SecurityContext
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               } else {
                   log.info("Token is not valid");
               }
           }

           filterChain.doFilter(request, response);
       }
       catch (ExpiredJwtException e){
           log.error("Token is expired");
           log.error(e.getMessage());
           writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
       }
       catch (Exception e) {
           log.error("Exception with filtering token " + e.getMessage());
           writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
       }
    }

    private void writeErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
