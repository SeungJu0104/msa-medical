package com.emr.slgi.reservation.dto;

import com.emr.slgi.reservation.enums.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationListByPatient {

    private String uuid;
    private String name;
    private LocalDateTime reservationDate;

}
