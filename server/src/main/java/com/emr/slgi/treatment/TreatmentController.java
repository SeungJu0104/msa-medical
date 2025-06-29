package com.emr.slgi.treatment;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/treatment")
public class TreatmentController {
	private final TreatmentService treatmentService;
	
	@PostMapping("/totalTreatment")
	public ResponseEntity<Object> totalTreatment(
			@RequestPart("data") TotalTreatmentDTO data,
			@RequestPart(value = "files",required = false )MultipartFile[] files){

		if (data.getTreatment().getDoctorUuid() == null || data.getTreatment().getPatientUuid() == null) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"생성되지 않았습니다.");
		}
		treatmentService.totalInsert(data,files);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/history")
	public ResponseEntity<Object> history(@RequestBody Treatment treatment){
		if(treatment.getPatientUuid() == null || treatment.getPatientUuid().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"환자 이름이 없습니다.");
		}
		List<Treatment> historyList =  treatmentService.getHistory(treatment);
		return ResponseEntity.ok(Map.of("list",historyList));
	}
	
	@GetMapping("/historyDetail/{id}")
	public ResponseEntity<Object> historyDetail(@PathVariable("id") int id ){
		if(id ==0 ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"환자의 접수 번호가 없습니다.");
		}
		TotalTreatmentDTO total = treatmentService.getTotalDetail(id);
		if (total == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "진료 상세 정보를 찾을 수 없습니다.");
	    }
		return ResponseEntity.ok(Map.of("total",total));	
				
	}
	

}