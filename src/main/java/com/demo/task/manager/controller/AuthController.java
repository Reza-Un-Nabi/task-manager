package com.demo.task.manager.controller;

import com.demo.task.manager.playload.request.LoginRequest;
import com.demo.task.manager.playload.request.SignupRequest;
import com.demo.task.manager.repository.UserRepository;
import com.demo.task.manager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    /**
     * This API is used to Login purpose.
     *
     * @param loginRequest
     * @return user information with jwt token
     */

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    /**
     * This API is used to create User account. Here we create Admin and Normal both User.
     *
     * @param signUpRequest
     * @return message
     */

    @PostMapping("/signupGeneralUser")
    public ResponseEntity<?> registerGeneralUser(@RequestBody SignupRequest signUpRequest) {
        return authService.createGeneralUser(signUpRequest);
    }

}

