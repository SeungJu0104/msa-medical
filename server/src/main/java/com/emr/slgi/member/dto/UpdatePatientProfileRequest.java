package com.emr.slgi.member.dto;

import com.emr.slgi.common.constants.RegexPatterns;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdatePatientProfileRequest {
    @Size(max = 20)
    private String name;

    @Pattern(regexp = RegexPatterns.PHONE)
    private String phone;
}
