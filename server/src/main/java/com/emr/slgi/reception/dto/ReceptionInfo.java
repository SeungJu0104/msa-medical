package com.emr.slgi.reception.dto;

import com.emr.slgi.common.constants.RegexPatterns;
import com.emr.slgi.reception.util.ReceptionErrorMessage;
import com.emr.slgi.util.ReservationErrorMessage;
import com.emr.slgi.util.Validate;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReceptionInfo {

    @NotBlank
    @Pattern(regexp = Validate.MEMBER_UUID_REGEX, message = ReservationErrorMessage.CHOOSE_DOCTOR)
    private String doctorUuid;

    @NotBlank
    @Pattern(regexp = Validate.MEMBER_UUID_REGEX, message = ReservationErrorMessage.CAN_NOT_FIND_PATIENT)
    private String patientUuid;

    @NotBlank
    @Size(min = 2, max = 6)
    private String patientName;

    @NotBlank
//    @Pattern(regexp = RegexPatterns.BIRTH_DATE, message = ReceptionErrorMessage.BIRTH_DATE_IS_NOT_VALID)
    private String birthDate;

    @NotBlank(message = ReservationErrorMessage.WRITE_SYMPTOM)
    @Size(min = 1, max = 100)
    private String symptom;

}
