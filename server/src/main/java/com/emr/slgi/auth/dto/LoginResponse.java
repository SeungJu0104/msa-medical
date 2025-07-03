package com.emr.slgi.auth.dto;

public record LoginResponse(
    String accessToken,
    String refreshToken
) {}
