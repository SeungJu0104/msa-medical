package com.emr.slgi.reception.dto;

import com.emr.slgi.reception.enums.ReceptionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReceptionStatus {

    private String uuid;
    private ReceptionStatus updateStatus;

}
