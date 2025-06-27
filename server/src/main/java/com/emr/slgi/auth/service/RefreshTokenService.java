package com.emr.slgi.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.emr.slgi.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-secret}")
    private String refreshTokenSecret;

    public String createRefreshToken(String memberUuid) {
        Map<String, String> map = Map.of(
            "uuid", memberUuid,
            "jtl", UUID.randomUUID().toString()
        );
        Date twoWeeksLater = Date.from(Instant.now().plus(14, ChronoUnit.DAYS));

        return jwtUtil.generateToken(map, twoWeeksLater, refreshTokenSecret);
    }

}
