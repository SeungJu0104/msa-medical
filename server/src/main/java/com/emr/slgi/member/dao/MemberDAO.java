package com.emr.slgi.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.member.domain.Member;
import com.emr.slgi.member.dto.DoctorUuidName;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.member.dto.MemberSearchDTO;
import com.emr.slgi.member.dto.PatientSummary;
import com.emr.slgi.member.dto.StaffSummary;

@Mapper
public interface MemberDAO {
    public Member getByUuid(String uuid);
    public List<DoctorUuidName> getDoctorList();
    public List<StaffSummary> getStaffList(String uuid);
    public List<PatientSummary> search(MemberSearchDTO memberSearchDTO);
    public void createPatient(MemberCreateDTO memberCreateDTO);
    public String getDoctorName(String uuid);
}
