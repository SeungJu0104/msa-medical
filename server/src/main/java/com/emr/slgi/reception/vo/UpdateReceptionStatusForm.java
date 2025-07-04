package com.emr.slgi.reception.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateReceptionStatusForm {

    @NotBlank
    private String uuid;

    @NotBlank
    private String patientUuid;

    @NotBlank
    private String doctorUuid;

    @NotBlank
    private String updateStatus;

}
