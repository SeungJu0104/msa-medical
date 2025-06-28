package com.emr.slgi.reception.service;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class WaitingList {

    private String uuid;
    private String patientUuid;
    private String status;
    private String name;
    private LocalDateTime createDate;

}
