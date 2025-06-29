package com.emr.slgi.reception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ReceptionStatus {

    WAITING("RE01", "대기"),
    CANCELRECEPTION("RE02", "접수 취소"),
    RECEPTIONCOMPLETE("RE03", "접수 완료");

    private final String code;
    private final String status;

    private static final Map<String, ReceptionStatus> map =
            Arrays.stream(values()).collect(Collectors.toMap(ReceptionStatus::getCode, e -> e));

    public static ReceptionStatus fromCode(String code) {
        return map.get(code);
    }

    public static String statusFromCode(String code) {
        return Optional.ofNullable(map.get(code))
                .map(ReceptionStatus::getStatus)
                .orElse(null);
    }

}


