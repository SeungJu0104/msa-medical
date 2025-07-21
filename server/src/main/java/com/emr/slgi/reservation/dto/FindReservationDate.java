package com.emr.slgi.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindReservationDate {

    private String patientUuid;
    private String doctorUuid;
    private LocalDateTime dateTime;

}
