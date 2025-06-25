package com.emr.slgi.reception.dto;

import com.emr.slgi.util.CommonErrorMessage;
import com.emr.slgi.util.ReservationErrorMessage;
import com.emr.slgi.util.Validate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceptionInfo {

    @NotEmpty
    @Pattern(regexp = Validate.MEMBER_UUID_REGEX, message = ReservationErrorMessage.CHOOSE_DOCTOR)
    private String doctorUuid;

    @NotEmpty
    @Pattern(regexp = Validate.MEMBER_UUID_REGEX, message = ReservationErrorMessage.CAN_NOT_FIND_PATIENT)
    private String patientUuid;

    @NotNull(message = ReservationErrorMessage.CHOOSE_DATE_TIME)
    private LocalDateTime dateTime;

    @NotEmpty
    @Size(min = 1, max = 300)
    private String symptom;

}
