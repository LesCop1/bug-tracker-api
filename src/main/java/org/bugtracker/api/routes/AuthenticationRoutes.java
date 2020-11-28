package org.bugtracker.api.routes;

import org.bugtracker.api.models.Developer;
import org.bugtracker.api.payloads.ApiResponse;
import org.bugtracker.api.payloads.JwtAuthenticationResponse;
import org.bugtracker.api.payloads.LoginRequest;
import org.bugtracker.api.payloads.SignUpRequest;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.bugtracker.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthenticationRoutes {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(JwtAuthenticationResponse.builder().accessToken(jwt).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (developerRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken !"), HttpStatus.BAD_REQUEST);
        }

        Developer user = Developer.builder()
                                    .username(signUpRequest.getUsername())
                                    .name(signUpRequest.getName())
                                    .build();

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Developer result = developerRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/developers/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}