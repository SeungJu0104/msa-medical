package com.emr.slgi.reservation.dto;

import com.emr.slgi.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
public class ReservationList {

    private String uuid;
    private String patientUuid;
    private String doctorUuid;
    private String name;
    private ReservationStatus status;
    private LocalDateTime reservationDate;

}
