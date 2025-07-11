package com.emr.slgi.reservation.dao;

import com.emr.slgi.reservation.dto.*;
import com.emr.slgi.reservation.enums.ReservationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<ReservationList> getReservationListByStaff(Map<String, Object> date);

    int cancelReservation(Set uuidForCancel);

    int changeReservation(String reservationId, LocalDateTime dateTime);

    List<ReservationList> getFullReservationList(@Param("doctorUuid") String doctorUuid, @Param("date") LocalDateTime date);

    int updateReservationStatus(String uuid, ReservationStatus status);

    List<ReservationListByPatient> getReservationListPerPatient(String patientUuid);
}
