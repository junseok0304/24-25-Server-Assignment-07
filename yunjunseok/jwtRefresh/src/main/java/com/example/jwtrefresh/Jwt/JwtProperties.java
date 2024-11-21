package com.example.jwtrefresh.Jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String secret;
    private Long accessTokenValidityInMilliseconds;
    private Long refreshTokenValidityInMilliseconds;
}
