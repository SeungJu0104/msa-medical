package com.emr.slgi.reception.enums;

import com.emr.slgi.reception.util.ReceptionErrorMessage;
import com.emr.slgi.util.CommonErrorMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
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
    @JsonValue // ENUM 상수명을 ENUM 내 한글 상태명으로 변경.
    private final String status;


    // 직렬화(상태코드를 ENUM 상수명으로 변경)
    private static final Map<String, ReceptionStatus> byCode =
            Arrays.stream(values()).collect(Collectors.toMap(ReceptionStatus::getCode, e -> e));

//    public static ReceptionStatus fromCode(String code) {
//        return byCode.get(code);
//    }

    // 역직렬화(한글 상태명을 ENUM 상수명으로 변경)
    private static final Map<String, ReceptionStatus> byStatus =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ReceptionStatus::getStatus, e -> e));

//    @JsonCreator // ENUM 상수명을 ENUM 내 상태코드로 변경
//    public static ReceptionStatus from(String statusText) {
//        return Optional.ofNullable(byStatus.get(statusText))
//                .orElseThrow(() -> new IllegalArgumentException(
//                        CommonErrorMessage.UPDATE_ERR + CommonErrorMessage.RETRY
//                ));
//    }

    @JsonCreator // JSON 요청 시 이 메서드로 역직렬화
    public static ReceptionStatus from(String input) {
        return Optional.ofNullable(byCode.get(input))       // 코드 기반
                .or(() -> Optional.ofNullable(byStatus.get(input))) // 상태명 기반
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ReceptionErrorMessage.RECEPTION_DATA_NOT_VALID));
    }

    public static boolean fromStatusTextExists(String statusText) {
        return byStatus.containsKey(statusText);
    }


}


