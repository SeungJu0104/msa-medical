package com.emr.slgi.auth.service;

import org.springframework.stereotype.Service;

import com.emr.slgi.auth.dao.CredentialsDAO;
import com.emr.slgi.auth.domain.Credentials;
import com.emr.slgi.auth.dto.CredentialsCreateParam;
import com.emr.slgi.auth.dto.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialsService {

    private final CredentialsDAO credentialsDAO;

    public void create(CredentialsCreateParam credentialsCreateParam) {
        credentialsDAO.create(credentialsCreateParam);
    }

    public boolean existsByUserid(String userid) {
        return credentialsDAO.existsByUserid(userid);
    }

    public Credentials getMemberCredentials(LoginRequest loginRequest) {
        return credentialsDAO.getMemberCredentials(loginRequest.getUserid());
    }

}
