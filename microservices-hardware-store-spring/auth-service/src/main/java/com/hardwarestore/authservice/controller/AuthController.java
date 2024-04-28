package com.hardwarestore.authservice.controller;

import com.hardwarestore.authservice.payload.request.LoginRequest;
import com.hardwarestore.authservice.payload.request.SignupRequest;
import com.hardwarestore.authservice.payload.request.TokenRefreshRequest;
import com.hardwarestore.authservice.service.RefreshTokenService;
import com.hardwarestore.authservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(userService.registerUser(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(tokenRefreshRequest));
    }
}
