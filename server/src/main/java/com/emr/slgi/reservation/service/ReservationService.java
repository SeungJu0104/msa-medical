package com.emr.slgi.reservation.service;

import com.emr.slgi.reservation.dao.ReservationDAO;
import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.ReservationForm;
import com.emr.slgi.reservation.dto.ReservationList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDAO rDao;

    public int makeReservation(ReservationForm rf) {

        int affectedRowsCount = rDao.getAffectedRowsCount(
                Map.of(
                        "select", "COUNT(*)",
                        "where", "PATIENT_UUID = '" + rf.getPatientUuid() + "' AND STATUS = 'RS03'"
                )
        );

        if(deleteHoldingReservation(rf) != affectedRowsCount) {
            return 0;
        }else {
            return rDao.makeReservation(rf);
        }

    }

    public int getAffectedRowsCount(Map reservationData) {
        return rDao.getAffectedRowsCount(reservationData);
    }

    public int deleteHoldingReservation(ReservationForm rf) {

        return rDao.deleteHoldingReservation(rf);

    }

    // 환자 직접 등록 시 날짜 선택하면 해당 일자의 예약 데이터 가져오기.
    public Optional<List<ReservationList>> getReservationList(FindReservationDate reservation) {
        
        // 오늘이면 today 값 true로
        if(reservation.getDateTime().toLocalDate().isEqual(LocalDate.now())) {
            reservation.setToday(true);
            log.info(reservation.toString());
        }

        return Optional.ofNullable(rDao.getReservationList(reservation));

    }

    public int holdReservation(FindReservationDate reservationDate) {

        return rDao.holdReservationDate(reservationDate);

    }

    public Optional<List<ReservationList>> getReservationList(OffsetDateTime dateTime) {

        return Optional.ofNullable(
            rDao.getReservationListByStaff(
                Map.of(
                    "start", dateTime.toLocalDate().atStartOfDay(),
                    "end", dateTime.toLocalDate().plusDays(1).atStartOfDay()
                )
            )
        );
    }

}
