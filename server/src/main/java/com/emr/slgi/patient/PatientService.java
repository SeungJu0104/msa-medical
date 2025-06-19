package com.emr.slgi.patient;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.emr.slgi.credentials.CredentialsDAO;
import com.emr.slgi.credentials.dto.CredentialsCreateDTO;
import com.emr.slgi.member.MemberDAO;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.patient.dto.SignupDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    private final MemberDAO memberDAO;
    private final CredentialsDAO credentialsDAO;

    public void signup(SignupDTO signupDTO) {
        // TODO: transaction 추가
        // TODO: 주민번호, 전화번호 중복 확인 후 처리
        // TODO: 아이디 중복 확인 후 처리
        String uuid = UUID.randomUUID().toString();
        MemberCreateDTO memberCreateDTO = new MemberCreateDTO(
            uuid,
            signupDTO.getName(),
            signupDTO.getRrn(),
            signupDTO.getPhone()
        );
        memberDAO.createPatient(memberCreateDTO);
        CredentialsCreateDTO credentialsCreateDTO = new CredentialsCreateDTO(
            uuid,
            signupDTO.getUserid(),
            signupDTO.getPassword()
        );
        credentialsDAO.create(credentialsCreateDTO);
    }
}
