package com.emr.slgi.treatment;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.emr.slgi.attachment.Attachment;
import com.emr.slgi.attachment.AttachmentDAO;
import com.emr.slgi.attachment.AttachmentService;
import com.emr.slgi.diagnosis.Diagnosis;
import com.emr.slgi.diagnosis.DiagnosisDAO;
import com.emr.slgi.page.PageDTO;
import com.emr.slgi.page.TreatmentHitoryRequestDTO;
import com.emr.slgi.page.PageResponseDTO;
import com.emr.slgi.prescription.Prescription;
import com.emr.slgi.prescription.PrescriptionDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TreatmentService {
	private final TreatmentDAO treatmentDAO;
	private final AttachmentService attachmentService;
	private final PrescriptionDAO prescriptionDAO;
	private final DiagnosisDAO diagnosisDAO;
	private final AttachmentDAO attachmentDAO;
	
	public void insertTreatment(String patientUuid , String doctorUuid) {
		treatmentDAO.insertTreatment(patientUuid,doctorUuid);
	}
	
	@Transactional
	public void totalInsert(TotalTreatmentDTO data, MultipartFile[] files) {
		
		Treatment treatment = data.getTreatment();
		treatmentDAO.updateTreatment(treatment);
		
		if(files != null && files.length>0) {
			attachmentService.insertAttachment(files, treatment.getId());
		}
		
		if (data.getPrescriptions() != null) {
			for (Prescription p : data.getPrescriptions()) {
		        p.setTreatmentId(treatment.getId());
		        prescriptionDAO.insertPrescription(p);
		    }
		}
		
		if (data.getDiagnosis() != null) {
			for (Diagnosis d : data.getDiagnosis()) {
		        d.setTreatmentId(treatment.getId());
		        diagnosisDAO.insertDiagnosis(d);
		    }
			
		}
	}

	public PageResponseDTO<Treatment> getHistory(TreatmentHitoryRequestDTO treatmentHitoryRequestDTO) {
		int totalCount = treatmentDAO.getHistoryCount(treatmentHitoryRequestDTO);
		List<Treatment> list = treatmentDAO.getHistory(treatmentHitoryRequestDTO);
		PageDTO pageDTO = new PageDTO(totalCount, treatmentHitoryRequestDTO.getPageNo(),treatmentHitoryRequestDTO.getSize());
		return new PageResponseDTO<Treatment>(pageDTO, list);
	}
	
	public TotalTreatmentDTO getTotalDetail(int id) {
		Treatment treatment = treatmentDAO.selectTreatment(id);
	    List<Prescription> prescriptions = prescriptionDAO.selectPrescriptions(id);
	    List<Diagnosis> diagnosis = diagnosisDAO.selectDiagnosis(id);
	    List<Attachment> attachments = attachmentDAO.selectAttachments(id);

	    TotalTreatmentDTO total = new TotalTreatmentDTO();
	    total.setTreatment(treatment);
	    total.setPrescriptions(prescriptions);
	    total.setDiagnosis(diagnosis);
	    total.setAttachments(attachments); 
		
		return total;
	}

	public Treatment selectedPatientUuid(String doctorUuid) {
		return treatmentDAO.selectedPatientUuid(doctorUuid);
	}

}
