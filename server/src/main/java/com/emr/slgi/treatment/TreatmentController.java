package com.emr.slgi.treatment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.diagnosis.Diagnosis;
import com.emr.slgi.diagnosis.DiagnosisDAO;
import com.emr.slgi.page.TreatmentHitoryRequestDTO;
import com.emr.slgi.prescription.Prescription;
import com.emr.slgi.prescription.PrescriptionDAO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/treatment")
public class TreatmentController {
	private final TreatmentService treatmentService;
	private final PrescriptionDAO prescriptionDAO;
	private final DiagnosisDAO  diagnosisDAO; 
	private final SimpMessageSendingOperations messagingTemplate;
	
	@PostMapping("/totalTreatment")
	public ResponseEntity<Object> totalTreatment(
			@RequestPart("data") TotalTreatmentDTO data,
			@RequestPart(value = "files",required = false )MultipartFile[] files){

		if (data.getTreatment().getId() == 0) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"생성되지 않았습니다.");
		}
		treatmentService.totalInsert(data,files);
		
	
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/history")
	public ResponseEntity<Object> history(@ModelAttribute TreatmentHitoryRequestDTO treatmentHitoryRequestDTO){
		messagingTemplate.convertAndSend("/sub/status", "{}");
		return ResponseEntity.ok(treatmentService.getHistory(treatmentHitoryRequestDTO));
	}
	
	@GetMapping("/historyDetail/{id}")
	public ResponseEntity<Object> historyDetail(@PathVariable("id")int id ){
		if(id ==0 ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"환자의 접수 번호가 없습니다.");
		}
		TotalTreatmentDTO total = treatmentService.getTotalDetail(id);
		if (total == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "진료 상세 정보를 찾을 수 없습니다.");
	    }
		return ResponseEntity.ok(Map.of("total",total));	
				
	}
	
	@GetMapping("/getDocument/{treatmentId}")
	public ResponseEntity<Object> getDocument(@PathVariable("treatmentId")int treatmentId ){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("medicineList", prescriptionDAO.selectPrescriptions(treatmentId));
		map.put("diseaseList", diagnosisDAO.selectDiagnosis(treatmentId));
		map.put("documentList", treatmentService.getDocument(treatmentId));
		
		return ResponseEntity.ok(map);
	}

}