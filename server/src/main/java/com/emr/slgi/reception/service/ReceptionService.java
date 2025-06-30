package com.emr.slgi.reception.service;

import com.emr.slgi.reception.dao.ReceptionDAO;
import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.WaitingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionDAO receptionDAO;
//    private final TreatmentDAO tDAO; // 현재 진료 중인 환자 가져오기 위해 임시로 작성.

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
}
