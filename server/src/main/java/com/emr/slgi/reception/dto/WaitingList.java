package com.emr.slgi.reception.dto;

import com.emr.slgi.reception.enums.ReceptionStatus;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaitingList {

    private String uuid;
    private String patientUuid;
    private ReceptionStatus status;
    private String name;
    private LocalDateTime createDate;
    private int turn;

}
