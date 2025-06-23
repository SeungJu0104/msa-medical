package com.emr.slgi.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindReservationDate {

    private String patientUuid; // 의료진 예약 대비
    private String doctorUuid;
    private LocalDateTime dateTime;
    private boolean isToday;

}
