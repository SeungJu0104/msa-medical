package com.emr.slgi.credentials.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CredentialsCreateDTO {
    private final String userUuid;
    private final String id;
    private final String password;
}
