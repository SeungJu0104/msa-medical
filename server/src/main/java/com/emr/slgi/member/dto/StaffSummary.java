package com.emr.slgi.member.dto;

import com.emr.slgi.member.enums.MemberRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffSummary {
    private String uuid;
    private MemberRole role;
    private String name;
}
