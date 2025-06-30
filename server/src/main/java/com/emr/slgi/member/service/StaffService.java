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
public class StaffService {
  private final MemberDAO memberDAO;

  public List<Member> getStaffList(String uuid) {
    List<Member> staffList = memberDAO.getStaffList(uuid);
    if (staffList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return staffList;
  }
}
