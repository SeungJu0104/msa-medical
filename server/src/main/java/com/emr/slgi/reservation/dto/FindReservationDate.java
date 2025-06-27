package com.emr.slgi.reservation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class FindReservationDate {

    private String patientUuid; // 의료진 예약 대비
    private String doctorUuid;
    private LocalDateTime dateTime;
    private boolean isToday;
//    private OffsetDateTime dateTime;

}
