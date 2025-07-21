package com.emr.slgi.status.service;

import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reservation.dto.ReservationStatusList;
import com.emr.slgi.status.dao.StatusDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusDAO statusDAO;

    @Transactional
    public List<ReceptionStatusList> getReceptionStatusList() {

        return statusDAO.getReceptionStatusList();

    }

    @Transactional
    public List<ReservationStatusList> getReservationStatusList() {

        return statusDAO.getReservationStatusList();

    }
}
