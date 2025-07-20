package com.emr.slgi.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.auth.domain.Credentials;
import com.emr.slgi.auth.dto.TokenResponse;
import com.emr.slgi.auth.dto.CredentialsCreateDTO;
import com.emr.slgi.auth.dto.LoginDTO;
import com.emr.slgi.auth.dto.LogoutDTO;
import com.emr.slgi.auth.dto.RefreshTokenDTO;
import com.emr.slgi.auth.dto.RegisterByPatientDTO;
import com.emr.slgi.auth.dto.UseridExistsResponse;
import com.emr.slgi.member.domain.Member;
import com.emr.slgi.member.dto.PatientRegisterDTO;
import com.emr.slgi.member.service.MemberService;
import com.emr.slgi.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final CredentialsService credentialsService;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.access-token-secret}")
    private String jwtSecret;

    @Transactional
    public void registerByPatient(RegisterByPatientDTO registerByPatientDTO) {
        String uuid = memberService.getUuidByRrn(registerByPatientDTO.getRrn())
            .orElseGet(() -> {
                PatientRegisterDTO patientRegisterDTO = new PatientRegisterDTO(
                    registerByPatientDTO.getName(),
                    registerByPatientDTO.getRrn(),
                    registerByPatientDTO.getPhone()
                );
                return memberService.createPatient(patientRegisterDTO);
            });
        String hashed = passwordEncoder.encode(registerByPatientDTO.getPassword());
        CredentialsCreateDTO credentialsCreateDTO = new CredentialsCreateDTO(
            uuid,
            registerByPatientDTO.getUserid(),
            hashed
        );
        credentialsService.create(credentialsCreateDTO);
    }

    public UseridExistsResponse checkIdDuplicate(String userid) {
        return new UseridExistsResponse(credentialsService.existsByUserid(userid));
    }

    public TokenResponse login(LoginDTO loginDTO) {
        Credentials credentials = credentialsService.getMemberCredentials(loginDTO);
        if (credentials == null || !passwordEncoder.matches(loginDTO.getPassword(), credentials.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다.");
        }
        return new TokenResponse(
            createAccessToken(credentials.getUserUuid()),
            refreshTokenService.createRefreshToken(credentials.getUserUuid())
        );
    }

    public String createAccessToken(String memberUuid) {
        Member member = memberService.getByUuid(memberUuid);

        Map<String, String> claims = Map.of(
            "uuid", member.getUuid(),
            "role", member.getRole().getCode()
        );
        Date thirtyMinutesLater = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));
        return jwtUtil.generateToken(claims, thirtyMinutesLater, jwtSecret);
    }

    public TokenResponse refreshToken(RefreshTokenDTO refreshTokenDTO) {
        Claims claims = refreshTokenService.parseRefreshToken(refreshTokenDTO.getRefreshToken());
        String jti = claims.get("jti", String.class);
        if (jti == null || !refreshTokenService.deleteWhitelist(jti)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }
        String uuid = claims.get("uuid", String.class);
        return new TokenResponse(
            createAccessToken(uuid),
            refreshTokenService.createRefreshToken(uuid)
        );
    }

    public void logout(LogoutDTO logoutDTO) {
        Claims claims = refreshTokenService.parseRefreshToken(logoutDTO.getRefreshToken());
        refreshTokenService.deleteWhitelist(claims.get("jti", String.class));
    }

}
