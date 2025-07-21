package com.emr.slgi.reservation.enums;

import com.emr.slgi.util.CommonErrorMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

    RESERVATION("RS01","예약"),
    CANCEL_RESERVATION("RS02", "예약 취소"),
    HOLD_RESERVATION("RS03", "예약 홀드"),
    COMPLETE_RESERVATION("RS04", "예약 완료"),
    CANCEL_HOLDING_RESERVATION("RS05", "예약 홀드 취소");

    private final String code;

    @JsonValue
    private final String status;

    private static final Map<String, ReservationStatus> map =
            Arrays.stream(values()).collect(Collectors.toMap(ReservationStatus::getCode, e -> e));

    public static ReservationStatus fromCode(String code) {
        return map.get(code);
    }

    private static final Map<String, ReservationStatus> byStatus =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ReservationStatus::getStatus, e -> e));

    @JsonCreator 
    public static ReservationStatus from(String statusText) {
        return Optional.ofNullable(byStatus.get(statusText))
                .orElseThrow(() -> new IllegalArgumentException(
                        CommonErrorMessage.UPDATE_ERR + CommonErrorMessage.RETRY
                ));
    }

    public static boolean fromStatusTextExists(String statusText) {
        return byStatus.containsKey(statusText);
    }


}
