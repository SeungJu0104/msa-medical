package com.emr.slgi.reception.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.emr.slgi.util.CommonErrorMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceptionStatus {

    WAITING("RE01", "대기"),
    CANCELRECEPTION("RE02", "접수 취소"),
    ONTREATMENT("RE03", "진료 중");

    private final String code;
    @JsonValue // ENUM 상수명을 ENUM 내 한글 상태명으로 변경.
    private final String status;


    // 직렬화(상태코드를 ENUM 상수명으로 변경)
    private static final Map<String, ReceptionStatus> map =
            Arrays.stream(values()).collect(Collectors.toMap(ReceptionStatus::getCode, e -> e));

    public static ReceptionStatus fromCode(String code) {
        return map.get(code);
    }

    // 역직렬화(한글 상태명을 ENUM 상수명으로 변경)
    private static final Map<String, ReceptionStatus> byStatus =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ReceptionStatus::getStatus, e -> e));

    @JsonCreator // ENUM 상수명을 ENUM 내 상태코드로 변경
    public static ReceptionStatus from(String statusText) {
        return Optional.ofNullable(byStatus.get(statusText))
                .orElseThrow(() -> new IllegalArgumentException(
                        CommonErrorMessage.UPDATE_ERR + CommonErrorMessage.RETRY
                ));
    }

    public static boolean fromStatusTextExists(String statusText) {
        return byStatus.containsKey(statusText);
    }

}


