package com.emr.slgi.reception.service;

import com.emr.slgi.reception.dao.ReceptionDAO;
import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.UpdateReceptionStatus;
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.reception.enums.ReceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionDAO receptionDAO;

    @Transactional
    public Map<String, ?> updateReceptionStatus (String uuid, String updateStatus) {

        ReceptionStatus status = ReceptionStatus.from(updateStatus);

        return Map.of(
                "status", status,
                "updateRes", receptionDAO.updateReceptionStatus(
                                    UpdateReceptionStatus.builder()
                                        .uuid(uuid)
                                        .updateStatus(status)
                                        .build()
                                    )
        );

    }

    @Transactional
    public int acceptPatientByStaff(ReceptionInfo receptionInfo) {
        return receptionDAO.acceptPatientByStaff(receptionInfo);
    }

    public List<WaitingList> getWaitingList(String doctorUuid) {

        List<WaitingList> waitingList = receptionDAO.getWaitingList(doctorUuid);

        return waitingList;

    }

    @Transactional
    public int checkOnTreatmentStatusExists(String doctorUuid) {

        int res = receptionDAO.checkOnTreatmentStatusExists(doctorUuid);

        return res;

    }

    @Transactional
    public int changeReceptionStatusToComplete(String uuid) {

        return receptionDAO.changeReceptionStatusToComplete(uuid);

    }

}
