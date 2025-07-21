package com.emr.slgi.reservation.vo;

import com.emr.slgi.util.ReservationErrorMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class ReservationCancelForm {

    @NotEmpty(message = ReservationErrorMessage.CAN_NOT_FIND_RESERVATION_DATE)
    private Set<String> uuidForCancel;

}
