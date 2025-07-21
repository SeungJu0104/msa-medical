package com.emr.slgi.reception.enums;

import com.emr.slgi.reception.util.ReceptionErrorMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ReceptionStatus {

    WAITING("RE01", "대기"),
    CANCEL_RECEPTION("RE02", "접수 취소"),
    ON_TREATMENT("RE03", "진료 중"),
    RECEPTION_COMPLETE("RE04", "진료 완료");

    private final String code;
    @JsonValue 
    private final String status;


    private static final Map<String, ReceptionStatus> byCode =
            Arrays.stream(values()).collect(Collectors.toMap(ReceptionStatus::getCode, e -> e));

    private static final Map<String, ReceptionStatus> byStatus =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ReceptionStatus::getStatus, e -> e));

    @JsonCreator 
    public static ReceptionStatus from(String input) {
        return Optional.ofNullable(byCode.get(input))       
                .or(() -> Optional.ofNullable(byStatus.get(input))) 
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ReceptionErrorMessage.RECEPTION_DATA_NOT_VALID));
    }

    public static boolean fromStatusTextExists(String statusText) {
        return byStatus.containsKey(statusText);
    }


}


