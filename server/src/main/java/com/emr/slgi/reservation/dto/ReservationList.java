package com.emr.slgi.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class ReservationList {

    private String patientUuid;
    private String doctorUuid;
    private LocalDateTime reservationDate;

}
