package com.emr.slgi.reception.dao;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.service.WaitingList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReceptionDAO {

    public int acceptPatientByStaff(ReceptionInfo receptionInfo);

    List<WaitingList> getWaitingList(String doctorUuid);

    // 팀장님께 Member 테이블 코드 작성 요청하기.
    String getDoctorNameByDoctor(String uuid);

    String getDoctorNameByNurse();

}
