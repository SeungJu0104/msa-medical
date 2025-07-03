package com.emr.slgi.member.dto;

import com.emr.slgi.common.constants.RegexPatterns;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientRegisterDTO {
    // TODO: record로 변경 테스트
    @NotBlank
    private final String name;

    @NotBlank
    @Pattern(regexp = RegexPatterns.RRN)
    private final String rrn;

    @NotBlank
    @Pattern(regexp = RegexPatterns.PHONE)
    private final String phone;
}
