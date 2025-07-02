package com.emr.slgi.member.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientRegisterDTO {
    // TODO: 패턴 추가 필요
    // TODO: record로 변경 테스트
    @NotBlank
    private final String name;

    @NotBlank
    private final String rrn;

    @NotBlank
    private final String phone;
}
