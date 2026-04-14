package com.ambar.ecommerce_backend.controller;

import com.ambar.ecommerce_backend.dto.AuthRequest;
import com.ambar.ecommerce_backend.model.User;
import com.ambar.ecommerce_backend.repository.UserRepository;
import com.ambar.ecommerce_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //username-admin password -1234
//    What does $2a$10$ mean?
//    $2a → BCrypt algorithm
//10 → strength (salt rounds)
//    Remaining → hashed value
//🔄 How login works then?
//
//    You might think:
//
//            "If password is hashed, how does login match?"
//
//            👉 Spring does this internally:
//
//            passwordEncoder.matches(rawPassword, encodedPassword);
//
//    So:
//
//    You enter: 1234
//    DB has: $2a$10$abc...
//    Spring compares → ✅ match
    @PostMapping("/register")

    public String register(@RequestBody AuthRequest request) {

        User user = new User();
//        {
//            "username": "user1",
//                "password": "1234"
//        }
//        {
//            "username": "admin",
//                "password": "1234"
//        }
        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔐 encode
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return "User registered successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
        return jwtUtil.generateToken(request.getUsername());
    }
}
