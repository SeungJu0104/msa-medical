package com.emr.slgi.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.auth.dto.LoginDTO;
import com.emr.slgi.auth.dto.RegisterByPatientDTO;
import com.emr.slgi.credentials.Credentials;
import com.emr.slgi.credentials.CredentialsDAO;
import com.emr.slgi.credentials.dto.CredentialsCreateDTO;
import com.emr.slgi.member.Member;
import com.emr.slgi.member.MemberDAO;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final MemberDAO memberDAO;
    private final CredentialsDAO credentialsDAO;
    private final JwtUtil jwtUtil;

    @Value("${jwt.access-token-secret}")
    private String jwtSecret;

    public void registerByPatient(RegisterByPatientDTO registerByPatientDTO) {
        // TODO: transaction 추가
        // TODO: 주민번호, 전화번호 중복 확인 후 처리
        // TODO: 아이디 중복 확인 후 처리
        String uuid = UUID.randomUUID().toString();
        MemberCreateDTO memberCreateDTO = new MemberCreateDTO(
            uuid,
            registerByPatientDTO.getName(),
            registerByPatientDTO.getRrn(),
            registerByPatientDTO.getPhone()
        );
        memberDAO.createPatient(memberCreateDTO);
        CredentialsCreateDTO credentialsCreateDTO = new CredentialsCreateDTO(
            uuid,
            registerByPatientDTO.getUserid(),
            registerByPatientDTO.getPassword()
        );
        credentialsDAO.create(credentialsCreateDTO);
    }

    public boolean checkIdDuplicate(String userid) {
        return credentialsDAO.existsByUserid(userid);
    }

    public Map<String, String> login(LoginDTO loginDTO) {
        Map<String, String> map = new HashMap<>();
        Credentials credentials = credentialsDAO.getMemberCredentials(loginDTO.getUserid());
        if (credentials == null || !credentials.getPassword().equals(loginDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다.");
        }
        map.put("accessToken", createAccessToken(credentials.getUserUuid()));
        return map;
    }

    public String createAccessToken(String memberUuid) {
        Member member = memberDAO.getByUuid(memberUuid);

        Map<String, String> map = Map.of(
            "uuid", member.getUuid(),
            "role", member.getRole().getCode()
        );
        Date thirtyMinutesLater = new Date(System.currentTimeMillis() + 30L * 60 * 1000);

        return jwtUtil.generateToken(map, thirtyMinutesLater, jwtSecret);
    }
}
