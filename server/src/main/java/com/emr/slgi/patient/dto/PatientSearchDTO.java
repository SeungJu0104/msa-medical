package com.emr.slgi.patient.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PatientSearchDTO {
  private final String searchValue;
}
