package com.emr.slgi.disease;

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
@RequestMapping("/disease")
public class DiseaseController {
	private final DiseaseService diseaseService;
	
	@GetMapping("/search")
	public ResponseEntity<Object> loadlist(@RequestParam("k") String keyword){
		if(keyword == null || keyword.trim().isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		List<Disease> word = diseaseService.getKeyword(keyword);
		return ResponseEntity.ok(word);
	}
	

}
