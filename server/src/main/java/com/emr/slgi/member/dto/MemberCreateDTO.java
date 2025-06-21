package com.emr.slgi.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateDTO {
    private final String uuid;
    private final String name;
    private final String rrn;
    private final String phone;
}
