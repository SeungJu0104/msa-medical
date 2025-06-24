package com.emr.slgi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emr.slgi.DAO.MemberDAO;
import com.emr.slgi.DTO.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public Member getById(String string) {
        return memberDAO.getById(string);
    }

	public List<Member> getList(String uuid) {
		return memberDAO.getList(uuid);
	}
}