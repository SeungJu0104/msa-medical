package com.emr.slgi.auth.dto;

import lombok.Data;

@Data
public class RegisterByPatientDTO {
    private String userid;
    private String password;
    private String name;
    private String rrn;
    private String phone;
}
