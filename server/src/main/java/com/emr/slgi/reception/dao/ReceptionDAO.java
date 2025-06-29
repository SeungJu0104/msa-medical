package com.emr.slgi.reception.dao;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reception.dto.WaitingList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReceptionDAO {

    public int acceptPatientByStaff(ReceptionInfo receptionInfo);

    List<WaitingList> getWaitingList(String doctorUuid);

    int cancelReception(String uuid);

    List<ReceptionStatusList> getReceptionStatusList();
}
