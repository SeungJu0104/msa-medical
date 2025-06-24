package com.emr.slgi.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.DTO.Member;

@Mapper
public interface MemberDAO {
    public Member getById(String string);

	public List<Member> getList(String uuid);   
}