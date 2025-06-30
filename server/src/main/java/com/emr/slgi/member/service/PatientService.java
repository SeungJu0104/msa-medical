package com.emr.slgi.member.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.emr.slgi.member.Member;
import com.emr.slgi.member.MemberDAO;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.member.dto.MemberSearchDTO;
import com.emr.slgi.member.dto.PatientRegisterDTO;
import com.emr.slgi.member.dto.PatientSearchDTO;

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

    public List<Member> search(PatientSearchDTO patientSearchDTO) {
        MemberSearchDTO memberSearchDTO = new MemberSearchDTO(patientSearchDTO.getSearchValue());
        return memberDAO.search(memberSearchDTO);
    }
}
