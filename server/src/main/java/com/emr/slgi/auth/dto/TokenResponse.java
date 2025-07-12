package com.emr.slgi.auth.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {}
