package com.emr.slgi.reception.service;

import com.emr.slgi.reception.dao.ReceptionDAO;
import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.UpdateReceptionStatus;
import com.emr.slgi.reception.vo.UpdateReceptionStatusForm;
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.reception.enums.ReceptionStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
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

    public int checkOnTreatmentStatusExists(String doctorUuid) {

        int res = receptionDAO.checkOnTreatmentStatusExists(doctorUuid);

        return res;

    }

    // 진료 완료 시 Status 변경
    public int changeReceptionStatusToComplete(String uuid) {

        return receptionDAO.changeReceptionStatusToComplete(uuid);

    }
}
