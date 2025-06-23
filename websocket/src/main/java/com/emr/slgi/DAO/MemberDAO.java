package com.emr.slgi.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
    public Member getById(String string);

	public List<Member> getList(String uuid);
    
}