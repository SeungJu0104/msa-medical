package com.emr.slgi.member;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public Member getById(String string) {
        return memberDAO.getById(string);
    }

	public List<Member> getList(String uuid) {
		// TODO Auto-generated method stub
		return memberDAO.getList(uuid);
	}

    
}
