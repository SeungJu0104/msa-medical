package com.emr.slgi.reception.dto;

import com.emr.slgi.reception.enums.ReceptionStatus;
import lombok.Data;

@Data
public class WaitingList {

    private String uuid;
    private String patientUuid;
    private String doctorUuid;
    private ReceptionStatus status;
    private String name;
    private int turn;

}
