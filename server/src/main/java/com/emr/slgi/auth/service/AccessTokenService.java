package com.emr.slgi.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.emr.slgi.auth.util.JwtUtil;
import com.emr.slgi.member.domain.Member;
import com.emr.slgi.member.service.MemberService;

import io.jsonwebtoken.Claims;

@Service
public class AccessTokenService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    public AccessTokenService(
        @Qualifier("accessJwtUtil") JwtUtil jwtUtil,
        MemberService memberService
    ) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }

    public String createAccessToken(String memberUuid) {
        Member member = memberService.getByUuid(memberUuid);

        Map<String, String> claims = Map.of(
            "uuid", member.getUuid(),
            "role", member.getRole().getCode()
        );
        Date thirtyMinutesLater = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));
        return jwtUtil.generateToken(claims, thirtyMinutesLater);
    }

    public Claims parseAccessToken(String accessToken) {
        return jwtUtil.parseToken(accessToken);
    }

}
