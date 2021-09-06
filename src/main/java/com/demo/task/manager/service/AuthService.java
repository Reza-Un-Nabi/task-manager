package com.demo.task.manager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.demo.task.manager.model.ERole;
import com.demo.task.manager.model.Roles;
import com.demo.task.manager.model.User;
import com.demo.task.manager.playload.request.LoginRequest;
import com.demo.task.manager.playload.request.SignupRequest;
import com.demo.task.manager.playload.response.JwtResponse;
import com.demo.task.manager.playload.response.MessageResponse;
import com.demo.task.manager.repository.RolesRepository;
import com.demo.task.manager.repository.UserRepository;
import com.demo.task.manager.security.jwt.JwtUtils;
import com.demo.task.manager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository roleRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    public ResponseEntity<?> createGeneralUser(SignupRequest signUpRequest) {

        MessageResponse messageResponse = new MessageResponse();
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            messageResponse.setStatus("failed");
            messageResponse.setMessage("\"Error: Username is already taken!\"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            messageResponse.setStatus("failed");
            messageResponse.setMessage("Error: Email is already in use!");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }


        // Create new user's account
        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "user":
                        Roles stuRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(stuRole);

                        break;

                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        messageResponse.setStatus("success");
        messageResponse.setMessage("User registered successfully!");
        return ResponseEntity.ok(messageResponse);
    }


}
