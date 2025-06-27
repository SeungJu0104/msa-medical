package com.emr.slgi.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class StringToKstLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        OffsetDateTime odt = OffsetDateTime.parse(source);
        return odt.atZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }
}

