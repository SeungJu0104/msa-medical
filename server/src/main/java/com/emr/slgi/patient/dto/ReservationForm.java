package com.emr.slgi.patient.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class ReservationForm {

    

    @NotEmpty(message = "의사를 선택해주세요.")
    @NotNull
    @Size(min=4, max=4)
    private String doctorUuid;

    @NotEmpty(message = "예약 날짜를 선택해주세요.")
    @NotNull
    private Date date;

    @NotEmpty(message = "예약 시간을 선택해주세요.")
    @NotNull
    private Time time;

    @NotEmpty(message = "증상을 입력해주세요.")
    @NotNull
    @Size(min = 1, max = 100)
    private String symptom;

}
