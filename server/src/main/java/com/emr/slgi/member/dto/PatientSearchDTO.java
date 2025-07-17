package com.emr.slgi.member.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientSearchDTO {
  @NotBlank
  private String searchValue;
}
