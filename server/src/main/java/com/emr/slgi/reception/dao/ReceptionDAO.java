package com.emr.slgi.reception.dao;

import com.emr.slgi.reception.dto.ReceptionInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceptionDAO {
    public int acceptPatientByStaff(ReceptionInfo receptionInfo);

}
