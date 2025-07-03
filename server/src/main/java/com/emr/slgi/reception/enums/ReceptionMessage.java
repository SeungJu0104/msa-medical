package com.emr.slgi.reception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceptionMessage {

    CANCEL_SUCCESS("접수가 취소됐습니다.");

    private final String message;

}
