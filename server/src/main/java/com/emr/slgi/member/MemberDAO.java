package com.emr.slgi.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.member.dto.MemberCreateDTO;

@Mapper
public interface MemberDAO {
    public Member getById(String id);
    public List<Member> getDoctorList();
    public void createPatient(MemberCreateDTO memberCreateDTO);
}
