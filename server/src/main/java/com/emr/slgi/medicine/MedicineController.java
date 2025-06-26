package com.emr.slgi.medicine;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicine")
public class MedicineController {
	private final MedicineService medicineService;
	
	@GetMapping("/search")
	public ResponseEntity<Object> loadlist(@RequestParam("k") String keyword){
		if(keyword == null || keyword.trim().isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		List<Medicine> word = medicineService.getKeyword(keyword);
		System.out.println(word);
		return ResponseEntity.ok(word);
	}
	

}
