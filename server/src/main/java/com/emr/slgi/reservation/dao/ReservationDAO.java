package com.emr.slgi.reservation.dao;

import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.ReservationForm;
import com.emr.slgi.reservation.dto.ReservationList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationDAO {

    int makeReservation(ReservationForm rf);

    List<ReservationList> getReservationList(
            FindReservationDate reservation
    );

}
