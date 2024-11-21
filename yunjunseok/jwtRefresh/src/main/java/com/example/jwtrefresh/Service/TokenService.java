package com.example.jwtrefresh.Service;

import com.example.jwtrefresh.Jwt.TokenProvider;
import com.example.jwtrefresh.Domain.RefreshToken;
import com.example.jwtrefresh.Domain.User;
import com.example.jwtrefresh.Repository.RefreshTokenRepository;
import com.example.jwtrefresh.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenService(TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createRefreshToken(String userEmail) {
        String refreshToken = tokenProvider.createRefreshToken(userEmail);
        refreshTokenRepository.deleteByUserEmail(userEmail);
        refreshTokenRepository.save(new RefreshToken(refreshToken, userEmail));
        return refreshToken;
    }

    public Optional<RefreshToken> validateRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Transactional
    public String refreshAccessToken(String refreshToken) {
        Optional<RefreshToken> storedToken = validateRefreshToken(refreshToken);
        if (storedToken.isPresent()) {
            Claims claims = tokenProvider.parseClaims(refreshToken);
            String userEmail = claims.getSubject();
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("요청하신 유저를 찾을 수 없습니다."));
            return tokenProvider.createAccessToken(user);
        } else {
            throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");
        }
    }

    @Transactional
    public void invalidateRefreshToken(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }
}
