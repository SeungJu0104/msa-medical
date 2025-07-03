package com.emr.slgi.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
  @NotBlank
  private String userid;

  @NotBlank
  private String password;
}
