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

    int holdReservation(FindReservationDate reservationDate);

    int cancelHoldingReservation(String patientUuid);

    int getAffectedRowsCount(Map reservationData);

    List<ReservationList> getReservationListByStaff(Map date);

    int cancelReservation(String reservationId);

}
