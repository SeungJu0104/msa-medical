package com.emr.slgi.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.TokenMgrError;

@Getter
@RequiredArgsConstructor
public enum ReceptionMessage {

    CANCEL_SUCCESS("접수가 취소됐습니다.");

    private final String message;

}
