package com.emr.slgi.reception.service;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class WaitingList {

    private String patientUuid;
    private String doctorUuid;
    private String status;
    private String name;
    private String rrn;
    private OffsetDateTime createDate;

}
