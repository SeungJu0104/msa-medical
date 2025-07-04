package com.emr.slgi.status.service;

import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reservation.dto.ReservationStatusList;
import com.emr.slgi.status.dao.StatusDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusDAO statusDAO;

    public List<ReceptionStatusList> getReceptionStatusList() {

        return statusDAO.getReceptionStatusList();

    }

    public List<ReservationStatusList> getReservationStatusList() {

        return statusDAO.getReservationStatusList();

    }
}
