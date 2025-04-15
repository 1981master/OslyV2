package com.master.oslyOnlineShoping.config;

import com.master.oslyOnlineShoping.entity.security.User;
import com.master.oslyOnlineShoping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

  // Uncomment if you're ready to use the repository
   private final UserRepository userRepository;
  private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

   public SecurityConfig(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .cors()
            .and()
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

    return username -> {
      User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//      logger.info("----------------------> passdddd:   "+user.getPassword().toString());
      return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(), // already encoded in DB
              user.getRoles().stream()
                      .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                      .collect(Collectors.toList()));
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
