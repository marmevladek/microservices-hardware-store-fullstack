package com.hardwarestore.authservice.service;

import com.hardwarestore.authservice.payload.request.TokenRefreshRequest;
import com.hardwarestore.authservice.payload.response.TokenRefreshResponse;

import java.util.Optional;

public interface RefreshTokenService {

    TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);
}
