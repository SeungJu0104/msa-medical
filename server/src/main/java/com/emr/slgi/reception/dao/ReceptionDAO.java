package com.emr.slgi.reception.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.UpdateReceptionStatus;
import com.emr.slgi.reception.dto.WaitingList;

@Mapper
public interface ReceptionDAO {

    public int acceptPatientByStaff(ReceptionInfo receptionInfo);

    List<WaitingList> getWaitingList(String doctorUuid);

    int cancelReception(String uuid);

    int updateReceptionStatus(UpdateReceptionStatus updateStatus);

    int updateWaitingStatusOnReception(@Param("uuid") String uuid, @Param("updateStatus")  String updateStatus);

    int checkOnTreatmentStatusExists(String doctorUuid);

    int changeReceptionStatusToComplete(String uuid);
}
