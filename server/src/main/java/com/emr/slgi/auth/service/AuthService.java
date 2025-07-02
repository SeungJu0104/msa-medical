package com.emr.slgi.auth.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.auth.domain.Credentials;
import com.emr.slgi.auth.dto.AccessTokenResponse;
import com.emr.slgi.auth.dto.CredentialsCreateDTO;
import com.emr.slgi.auth.dto.LoginDTO;
import com.emr.slgi.auth.dto.LoginResponse;
import com.emr.slgi.auth.dto.LogoutDTO;
import com.emr.slgi.auth.dto.RefreshTokenDTO;
import com.emr.slgi.auth.dto.RegisterByPatientDTO;
import com.emr.slgi.auth.dto.UseridExistsResponse;
import com.emr.slgi.member.domain.Member;
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

    @Value("${jwt.access-token-secret}")
    private String jwtSecret;

    public void registerByPatient(RegisterByPatientDTO registerByPatientDTO) {
        // TODO: transaction 추가
        // TODO: 주민번호, 전화번호 중복 확인 후 처리
        // TODO: 아이디 중복 확인 후 처리
        String uuid = memberService.createPatient(registerByPatientDTO);
        CredentialsCreateDTO credentialsCreateDTO = new CredentialsCreateDTO(
            uuid,
            registerByPatientDTO.getUserid(),
            registerByPatientDTO.getPassword()
        );
        credentialsService.create(credentialsCreateDTO);
    }

    public UseridExistsResponse checkIdDuplicate(String userid) {
        return new UseridExistsResponse(credentialsService.existsByUserid(userid));
    }

    public LoginResponse login(LoginDTO loginDTO) {
        Credentials credentials = credentialsService.getMemberCredentials(loginDTO);
        if (credentials == null || !credentials.getPassword().equals(loginDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다.");
        }
        return new LoginResponse(
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
        Date thirtyMinutesLater = new Date(System.currentTimeMillis() + 30L * 60 * 1000);

        return jwtUtil.generateToken(claims, thirtyMinutesLater, jwtSecret);
    }

    public AccessTokenResponse refreshToken(RefreshTokenDTO refreshTokenDTO) {
        Claims claims = refreshTokenService.parseRefreshToken(refreshTokenDTO.getRefreshToken());
        String jti = claims.get("jti", String.class);
        if (jti == null || refreshTokenService.isTokenBlacklisted(jti)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
        }
        String uuid = claims.get("uuid", String.class);
        return new AccessTokenResponse(createAccessToken(uuid));
    }

    public void logout(LogoutDTO logoutDTO) {
        Claims claims = refreshTokenService.parseRefreshToken(logoutDTO.getRefreshToken());
        refreshTokenService.blacklistTokenJti(
            claims.get("jti", String.class),
            claims.getExpiration()
        );
    }
}
