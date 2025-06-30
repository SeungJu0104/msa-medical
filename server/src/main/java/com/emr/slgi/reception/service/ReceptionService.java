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
//    private final TreatmentDAO tDAO; // 현재 진료 중인 환자 가져오기 위해 임시로 작성.

    public int updateReceptionStatus (String uuid, String updateStatus) {

        ReceptionStatus status = ReceptionStatus.from(updateStatus);

        return receptionDAO.updateReceptionStatus(
                UpdateReceptionStatus.builder()
                        .uuid(uuid)
                        .updateStatus(status)
                        .build()
        );
        // 역매퍼 동작 체크하기
    }


    public int acceptPatientByStaff(ReceptionInfo receptionInfo) {
        return receptionDAO.acceptPatientByStaff(receptionInfo);
    }

    public List<WaitingList> getWaitingList(String doctorUuid) {

        List<WaitingList> waitingList = receptionDAO.getWaitingList(doctorUuid);

//        현재 대기중인 환자 리스트에 진료 중인 환자 진료 테이블에 가져와서 추가. 함수명은 임의로 작성.
//        waitingList.add(tDAO.getTreatingPatientInfo());

        return waitingList;

    }

    public int cancelReception(String uuid) {

        return receptionDAO.cancelReception(uuid);

    }

    public Optional<List<ReceptionStatusList>> getReceptionStatusList() {

        return Optional.ofNullable(receptionDAO.getReceptionStatusList());

    }

}
