package com.emr.slgi.reservation.dao;

import com.emr.slgi.reservation.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    int cancelReservation(Set uuidForCancel);

    int changeReservation(String reservationId, LocalDateTime dateTime);

    List<ReservationList> getFullReservationList(String doctorUuid, LocalDateTime date);

    int updateReservationStatus(ReservationList build);

    List<ReservationListByPatient> getReservationListPerPatient(String patientUuid);
}
