package com.hardwarestore.authservice.service;

import com.hardwarestore.authservice.payload.request.LoginRequest;
import com.hardwarestore.authservice.payload.request.SignupRequest;
import com.hardwarestore.authservice.payload.response.JwtResponse;
import com.hardwarestore.authservice.payload.response.MessageResponse;


public interface UserService {

    JwtResponse authenticate(LoginRequest loginRequest);

    MessageResponse registerUser(SignupRequest signupRequest);

}
