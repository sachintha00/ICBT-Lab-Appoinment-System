package com.labappoinment.labappoinment.auth;

import com.labappoinment.labappoinment.config.JwtService;
import com.labappoinment.labappoinment.model.UserModel;
import com.labappoinment.labappoinment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserModel request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);
        return ResponseEntity.ok().body(Map.of("response", "User created successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                "sachintha@gmail.com",
                "password123"
        ));
        var user = userRepository.findByEmail(request.getUsername()).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok().body(Map.of("response", "Successfully Authenticated", "token", token));
    }
}
