package com.emr.slgi.auth.dto;

import com.emr.slgi.common.constants.RegexPatterns;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LogoutDTO {
    @NotBlank
    @Pattern(regexp = RegexPatterns.JWT_TOKEN)
    private String refreshToken;
}
