package com.emr.slgi.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
    public Member getById(String id);
    public List<Member> getDoctorList();
}
