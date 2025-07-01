package com.emr.slgi.member.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PatientSearchDTO {
  @NotBlank
  private final String searchValue;
}
