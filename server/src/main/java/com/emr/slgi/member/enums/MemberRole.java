package com.emr.slgi.member.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    ADMIN   ("R001", "ROLE_ADMIN"),
    DOCTOR  ("R002", "ROLE_DOCTOR"),
    NURSE   ("R003", "ROLE_NURSE"),
    PATIENT ("R004", "ROLE_PATIENT");

    private static final Map<String, MemberRole> BY_CODE =
        Arrays.stream(values()).collect(Collectors.toMap(MemberRole::getCode, e -> e));

    private final String code;
    private final String authority;

    public static MemberRole fromCode(String code) {
        MemberRole role = BY_CODE.get(code);
        if (role == null) {
            throw new IllegalArgumentException("Unknown Role code: " + code);
        }
        return role;
    }
}
