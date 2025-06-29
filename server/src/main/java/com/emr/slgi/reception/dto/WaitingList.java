package com.emr.slgi.reception.dto;

import com.emr.slgi.reception.enums.ReceptionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WaitingList {

    private String uuid;
    private String patientUuid;
    private String status;
    private String name;
    private LocalDateTime createDate;

}
