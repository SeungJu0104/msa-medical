package com.emr.slgi.member;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public Member getById(String id) {
        return memberDAO.getById(id);
    }

    public List<Member> getDoctorList() {
        return memberDAO.getDoctorList();
    }
}
