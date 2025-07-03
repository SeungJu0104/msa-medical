package com.emr.slgi.reservation.service;

import com.emr.slgi.reception.dao.ReceptionDAO;
import com.emr.slgi.reception.enums.ReceptionStatus;
import com.emr.slgi.reservation.dao.ReservationDAO;
import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.ReservationForm;
import com.emr.slgi.reservation.dto.ReservationList;
import com.emr.slgi.reservation.dto.ReservationStatusList;
import com.emr.slgi.reservation.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDAO rDao;
    private final ReceptionDAO receptionDAO;

    public int makeReservation(ReservationForm rf) {

        if(!cancelHoldingReservation(rf.getPatientUuid())) {
            return -1;
        }else {
            return rDao.makeReservation(rf);
        }

    }

    public int getAffectedRowsCount(Map reservationData) {
        return rDao.getAffectedRowsCount(reservationData);
    }

    public boolean cancelHoldingReservation(String patientUuid) {

        int affectedRowsCount = getAffectedRowsCount(
                Map.of(
                        "where", "PATIENT_UUID = '550e8400-e29b-41d4-a716-446655440020' AND STATUS = 'RS03'"
                        // 임시 환자 UUID
                        // "PATIENT_UUID = " + reservationDate.getDoctorUuid() + " AND STATUS = 'RS03'"
                )
        );

        if(rDao.cancelHoldingReservation(patientUuid) != affectedRowsCount) {
            return false;
        }else{
            return true;
        }


    }

    // 환자 직접 등록 시 날짜 선택하면 해당 일자의 예약 데이터 가져오기.
    public Optional<List<ReservationList>> getReservationList(FindReservationDate reservation) {
        
        // 오늘이면 today 값 true로
        if(reservation.getDateTime().toLocalDate().isEqual(LocalDate.now())) {
            reservation.setToday(true);
        } else {
            reservation.setDateTime(reservation.getDateTime().toLocalDate().atStartOfDay());
        }

        return Optional.ofNullable(rDao.getReservationList(reservation));

    }

    public int holdReservation(FindReservationDate reservationDate) {

        reservationDate.setPatientUuid("550e8400-e29b-41d4-a716-446655440020"); // 임시 환자 데이터

        if(!cancelHoldingReservation(reservationDate.getPatientUuid())) {
            return -1;
        }

        return rDao.holdReservation(reservationDate);

    }

    public Optional<List<ReservationList>> getReservationList(LocalDateTime dateTime) {

        return Optional.ofNullable(
            rDao.getReservationListByStaff(
                Map.of(
                    "start", dateTime.toLocalDate().atStartOfDay(),
                    "end", dateTime.toLocalDate().plusDays(1).atStartOfDay()
                )
            )
        );

    }

    public boolean cancelReservation(String reservationId) {

        log.info(reservationId);

        int affectedRowsCount = getAffectedRowsCount(
                Map.of(
                        "where", "ID = '" + reservationId + "' AND STATUS = 'RS01'"
                )
        );


        if(rDao.cancelReservation(reservationId) != affectedRowsCount) {
            return false;
        } else {
            return true;
        }


    }

    public boolean changeReservation(String reservationId, LocalDateTime dateTime) {

        if(rDao.changeReservation(reservationId, dateTime) != 1) {
            return false;
        }else {
            return true;
        }

    }

    public Optional<List<ReservationList>> getFullReservationList(String doctorUuid, LocalDateTime date) {
        log.info("날짜 : {}", String.valueOf(date));
        return Optional.ofNullable(rDao.getFullReservationList(doctorUuid, date));

    }

    public int updateReservationStatus(String uuid, String updateStatus) {

        ReservationStatus status = ReservationStatus.from(updateStatus);

        return rDao.updateReservationStatus(
            ReservationList.builder()
                    .uuid(uuid)
                    .status(status)
                    .build()
        );

    }

    public int updateWaitingStatusOnReception(String uuid, String updateStatus) {

        return receptionDAO.updateWaitingStatusOnReception(uuid, updateStatus);

    }
}
