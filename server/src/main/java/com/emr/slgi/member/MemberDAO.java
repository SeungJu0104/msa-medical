package com.emr.slgi.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.member.dto.MemberSearchDTO;

@Mapper
public interface MemberDAO {
    public Member getById(String id);
    public List<Member> getDoctorList();
    public List<Member> getStaffList();
    public List<Member> search(MemberSearchDTO memberSearchDTO);
    public void createPatient(MemberCreateDTO memberCreateDTO);
}
