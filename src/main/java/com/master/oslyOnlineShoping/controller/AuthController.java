package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String token = jwtUtil.generateToken(String.valueOf(userDetails));
//
//            return ResponseEntity.ok(new LoginResponse(token));
//        } catch (BadCredentialsException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
    //}
}



/*
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role defaultRole = roleRepository.findByName("user").get(); // lowercase!

        if (defaultRole == null) {
            throw new RuntimeException("Default role 'user' not found in the database.");
        }

        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);

        return "redirect:/login";
    }

}
*/



