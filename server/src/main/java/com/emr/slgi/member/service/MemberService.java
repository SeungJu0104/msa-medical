package com.emr.slgi.member.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.member.Member;
import com.emr.slgi.member.MemberDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    public Member getByUuid(String uuid) {
        return memberDAO.getByUuid(uuid);
    }

    public List<Member> getDoctorList() {
        return memberDAO.getDoctorList();
    }

    public String getDoctorName(String uuid) {
        String name = memberDAO.getDoctorName(uuid);
        if (name == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "의사를 찾을 수 없습니다.");
        }
        return name;
    }
}
