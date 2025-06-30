package com.emr.slgi.member.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSummary {
    private String uuid;
    private String name;
    private String rrn;
    private String phone;
    private LocalDateTime createDate;
}
