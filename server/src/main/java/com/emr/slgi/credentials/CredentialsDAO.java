package com.emr.slgi.credentials;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.credentials.dto.CredentialsCreateDTO;

@Mapper
public interface CredentialsDAO {
    public void create(CredentialsCreateDTO credentialsCreateDTO);
    public boolean existsByUserid(String userid);
    public Credentials getMemberCredentials(String userid);
}
