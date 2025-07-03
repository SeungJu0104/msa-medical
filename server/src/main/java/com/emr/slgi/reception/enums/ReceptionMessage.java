package com.emr.slgi.reception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceptionMessage {

    CANCEL_SUCCESS("접수가 취소됐습니다."),
    CHANGE_STATUS_ON_TREATMENT("진료 중으로 변경됐습니다.");


    private final String message;

}
