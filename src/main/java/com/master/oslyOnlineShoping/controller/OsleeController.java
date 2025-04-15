package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.dto.LoginRequest;
import com.master.oslyOnlineShoping.util.JwtResponse;
import com.master.oslyOnlineShoping.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class OsleeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public OsleeController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    logger.info("Login attempt for user: {}", request.getUsername());

    // Authenticate user
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );

    // Set authentication in security context
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Generate JWT token
    String jwt = jwtUtil.generateToken(request.getUsername());

    // Return response with JWT token
    return ResponseEntity.ok(new JwtResponse(jwt));
  }
}
