package com.emr.slgi.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.auth.domain.Credentials;
import com.emr.slgi.auth.dto.TokenResponse;
import com.emr.slgi.auth.dto.CredentialsCreateParam;
import com.emr.slgi.auth.dto.LoginRequest;
import com.emr.slgi.auth.dto.LogoutRequest;
import com.emr.slgi.auth.dto.RefreshTokenRequest;
import com.emr.slgi.auth.dto.RegisterByPatientRequest;
import com.emr.slgi.auth.dto.UseridExistsResponse;
import com.emr.slgi.member.dto.PatientRegisterDTO;
import com.emr.slgi.member.service.MemberService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AccessTokenService accessTokenService;
    private final CredentialsService credentialsService;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerByPatient(RegisterByPatientRequest registerByPatientRequest) {
        String uuid = memberService.getUuidByRrn(registerByPatientRequest.getRrn())
            .orElseGet(() -> {
                PatientRegisterDTO patientRegisterDTO = new PatientRegisterDTO(
                    registerByPatientRequest.getName(),
                    registerByPatientRequest.getRrn(),
                    registerByPatientRequest.getPhone()
                );
                return memberService.createPatient(patientRegisterDTO);
            });
        String hashed = passwordEncoder.encode(registerByPatientRequest.getPassword());
        CredentialsCreateParam credentialsCreateParam = new CredentialsCreateParam(
            uuid,
            registerByPatientRequest.getUserid(),
            hashed
        );
        try {
            credentialsService.create(credentialsCreateParam);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 회원입니다.");
        }
    }

    public UseridExistsResponse checkIdDuplicate(String userid) {
        return new UseridExistsResponse(credentialsService.existsByUserid(userid));
    }

    public TokenResponse login(LoginRequest loginRequest) {
        Credentials credentials = credentialsService.getMemberCredentials(loginRequest);
        if (credentials == null || !passwordEncoder.matches(loginRequest.getPassword(), credentials.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다.");
        }
        return new TokenResponse(
            accessTokenService.createAccessToken(credentials.getUserUuid()),
            refreshTokenService.createRefreshToken(credentials.getUserUuid())
        );
    }

    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Claims claims = refreshTokenService.parseRefreshToken(refreshTokenRequest.getRefreshToken());
        String jti = claims.get("jti", String.class);
        if (jti == null || !refreshTokenService.deleteWhitelist(jti)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }
        String uuid = claims.get("uuid", String.class);
        return new TokenResponse(
            accessTokenService.createAccessToken(uuid),
            refreshTokenService.createRefreshToken(uuid)
        );
    }

    public void logout(LogoutRequest logoutRequest) {
        Claims claims = refreshTokenService.parseRefreshToken(logoutRequest.getRefreshToken());
        refreshTokenService.deleteWhitelist(claims.get("jti", String.class));
    }

}
