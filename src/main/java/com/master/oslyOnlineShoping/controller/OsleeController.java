package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.dto.LoginRequest;
import com.master.oslyOnlineShoping.dto.SignupRequest;
import com.master.oslyOnlineShoping.entity.security.Role;
import com.master.oslyOnlineShoping.entity.security.User;
import com.master.oslyOnlineShoping.repository.RoleRepository;
import com.master.oslyOnlineShoping.repository.UserRepository;
import com.master.oslyOnlineShoping.service.ServiceMail;
import com.master.oslyOnlineShoping.util.JwtResponse;
import com.master.oslyOnlineShoping.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class OsleeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final RoleRepository roleRepository;

  private final ServiceMail serviceMail;

  public OsleeController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ServiceMail serviceMail) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.serviceMail = serviceMail;
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

  @PostMapping("/verify-code")
  public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    String code = request.get("code");

    // Fetch user from DB using email
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("User not found");
    }

    User user = userOpt.get();

    // Check if the verification code exists and matches
    if (user.getVerificationCode() != null &&
            user.getVerificationCode().equals(code)) {

      // Check if the verification code has expired
      if (user.getCodeExpiry() != null && user.getCodeExpiry().isAfter(LocalDateTime.now())) {
        // If valid, mark the user as verified
        user.setIsVerified("Y");
        user.setVerificationCode(null);  // Clear verification code
        user.setCodeExpiry(null);         // Clear expiry date
        userRepository.save(user);        // Save updated user status
        return ResponseEntity.ok("Verification successful");
      } else {
        return ResponseEntity.badRequest().body("Verification code has expired");
      }
    } else {
      return ResponseEntity.badRequest().body("Invalid verification code");
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
    logger.info("Signup attempt for user: {}", request.getUsername());

    if (userRepository.existsByUsername(request.getUsername())) {
      return ResponseEntity.badRequest().body("Username is already taken");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      return ResponseEntity.badRequest().body("Email is already in use");
    }

    // Create new user
    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setVerificationCode(String.format("%05d", new Random().nextInt(100000)));
    user.setCodeExpiry(LocalDateTime.now().plusMinutes(15));
    user.setIsVerified("N");
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // Load existing role from DB
    Role userRole = roleRepository.findByName("user")
            .orElseThrow(() -> new RuntimeException("Error: Role not found"));

    user.setRoles(Set.of(userRole));
    userRepository.saveAndFlush(user);

    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/emailVerfi")
  public ResponseEntity<?> emailVerification(@RequestBody Map<String, String> userData) {
    String email = userData.get("email"); // Get email from request body

    try {
      // Retrieve the user from DB
      Optional<User> user = userRepository.findByEmail(email);
      if (user.isEmpty()) {
        return ResponseEntity.badRequest().body("User not found");
      }

      // Generate or retrieve the verification code and set expiry time
      String verificationCode = user.get().getVerificationCode();
      LocalDateTime expiryTime = user.get().getCodeExpiry();

      // Check if the verification code is expired
      if (LocalDateTime.now().isAfter(expiryTime)) {
        return ResponseEntity.badRequest().body("Verification code has expired");
      }

      // Send email with the verification code
      String emailContent = "Your email verification code is: " + verificationCode;
      serviceMail.sendMail(email, emailContent);

      logger.info("Verification code sent to: {}", email);
      return ResponseEntity.ok("Verification code sent successfully");

    } catch (Exception ex) {
      logger.error("Error sending verification code", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending verification code");
    }
  }



}
