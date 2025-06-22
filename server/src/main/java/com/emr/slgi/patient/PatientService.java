package com.emr.slgi.patient;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.emr.slgi.member.MemberDAO;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.patient.dto.PatientRegisterDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final MemberDAO memberDAO;

    public void createPatient(PatientRegisterDTO patientRegisterDTO) {
        String uuid = UUID.randomUUID().toString();
        MemberCreateDTO memberCreateDTO = new MemberCreateDTO(
            uuid,
            patientRegisterDTO.getName(),
            patientRegisterDTO.getRrn(),
            patientRegisterDTO.getPhone()
        );
        memberDAO.createPatient(memberCreateDTO);
    }
}
