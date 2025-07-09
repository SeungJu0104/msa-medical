package com.emr.slgi.auth.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.emr.slgi.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.refresh-token-secret}")
    private String refreshTokenSecret;

    public String createRefreshToken(String memberUuid) {
        String jti = UUID.randomUUID().toString();
        Map<String, String> map = Map.of(
            "uuid", memberUuid,
            "jti", jti
        );
        Date twoWeeksLater = Date.from(Instant.now().plus(14, ChronoUnit.DAYS));

        String refreshToken = jwtUtil.generateToken(map, twoWeeksLater, refreshTokenSecret);
        whitelistTokenJti(jti, twoWeeksLater);
        return refreshToken;
    }

    public Claims parseRefreshToken(String refreshToken) {
        return jwtUtil.parseToken(refreshToken, refreshTokenSecret);
    }

    public void whitelistTokenJti(String jti, Date exp) {
        String key = "whitelist:jti:" + jti;
        Duration remaining = Duration.between(Instant.now(), exp.toInstant());
        remaining = remaining.isNegative() ? Duration.ZERO : remaining;
        stringRedisTemplate.opsForValue().set(key, "1", remaining);
    }

    public boolean deleteWhitelist(String jti) {
        String key = "whitelist:jti:" + jti;
        return stringRedisTemplate.delete(key);
    }

}
