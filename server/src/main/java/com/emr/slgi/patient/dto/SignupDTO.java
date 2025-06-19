package com.emr.slgi.patient.dto;

import lombok.Data;

@Data
public class SignupDTO {
    private String userid;
    private String password;
    private String name;
    private String rrn;
    private String phone;
}
