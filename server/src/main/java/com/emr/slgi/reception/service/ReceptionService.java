package com.emr.slgi.reception.service;

import com.emr.slgi.reception.dao.ReceptionDAO;
import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reception.dto.UpdateReceptionStatus;
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.reception.enums.ReceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionDAO receptionDAO;

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


    public int acceptPatientByStaff(ReceptionInfo receptionInfo) {
        return receptionDAO.acceptPatientByStaff(receptionInfo);
    }

    public List<WaitingList> getWaitingList(String doctorUuid) {

        List<WaitingList> waitingList = receptionDAO.getWaitingList(doctorUuid);

        return waitingList;

    }

    public int cancelReception(String uuid) {

        return receptionDAO.cancelReception(uuid);

    }

    public Optional<List<ReceptionStatusList>> getReceptionStatusList() {

        return Optional.ofNullable(receptionDAO.getReceptionStatusList());

    }

}
