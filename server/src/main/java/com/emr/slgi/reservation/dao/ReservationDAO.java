package com.emr.slgi.reservation.dao;

import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.ReservationForm;
import com.emr.slgi.reservation.dto.ReservationList;
import org.apache.ibatis.annotations.Mapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationDAO {

    int makeReservation(ReservationForm rf);

    List<ReservationList> getReservationList(
            FindReservationDate reservation
    );

    int holdReservationDate(FindReservationDate reservationDate);

    int deleteHoldingReservation(ReservationForm rf);

    int getAffectedRowsCount(Map reservationData);

    List<ReservationList> getReservationListByStaff(Map date);

}
