package com.emr.slgi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.DTO.ChatJoin;
import com.emr.slgi.service.ChatJoinService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatjoin")
public class ChatJoinController {
	private final ChatJoinService chatJoinService;
	
	@PutMapping("/updateJoinTime")
	public ResponseEntity<Object> updateJoinTime(@RequestBody ChatJoin join){
		boolean result = chatJoinService.updateJoinTime(join);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입장시간을갱신하지 못했습니다");
		}
	}
	
	
	@PutMapping("/updateOutTime")
	public ResponseEntity<Object> updateOutTime(@RequestBody ChatJoin join){
		boolean result = chatJoinService.updateOutTime(join);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("퇴장시간을갱신하지 못했습니다");
		}
	}	
	
	

}
