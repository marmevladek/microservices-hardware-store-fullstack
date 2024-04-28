package com.hardwarestore.authservice.service.impl;

import com.hardwarestore.authservice.exception.UserNotFoundException;
import com.hardwarestore.authservice.model.RefreshToken;
import com.hardwarestore.authservice.model.User;
import com.hardwarestore.authservice.payload.request.TokenRefreshRequest;
import com.hardwarestore.authservice.payload.response.TokenRefreshResponse;
import com.hardwarestore.authservice.repository.RefreshTokenRepository;
import com.hardwarestore.authservice.repository.UserRepository;
import com.hardwarestore.authservice.security.jwt.JwtUtils;
import com.hardwarestore.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Value("${app.jwtRefreshExpireMs}")
    private Long refreshTokenDurationMs;

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        RefreshToken token = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException(requestRefreshToken + " Refresh token is not in database"));

        RefreshToken deletedToken = verifyExpiration(token);

        User userRefreshToken = deletedToken.getUser();

        String newToken = jwtUtils.generateJwtToken(userRefreshToken.getUsername());

        return new TokenRefreshResponse(newToken, requestRefreshToken);
    }

    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found")));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
        }

        return token;
    }
}
