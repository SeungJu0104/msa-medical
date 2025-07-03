package com.emr.slgi.reservation.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class ReservationCancelForm {

    @NotEmpty
    private Set uuidForCancel;

}
