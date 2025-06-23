package com.emr.slgi.reservation.dto;

import lombok.Data;

@Data
public class ReservationList {

    private String patientUuid;
    private String doctorUuid;
    private String reservationDate;

}
