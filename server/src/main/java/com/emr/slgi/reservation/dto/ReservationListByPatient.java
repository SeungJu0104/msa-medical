package com.emr.slgi.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationListByPatient {

    private String uuid;
    private String name;
    private LocalDateTime slot;

}
