package com.emr.slgi.status.dao;

import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reservation.dto.ReservationStatusList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatusDAO {

    List<ReceptionStatusList> getReceptionStatusList();

    List<ReservationStatusList> getReservationStatusList();

}
