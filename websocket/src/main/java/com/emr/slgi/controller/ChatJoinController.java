package com.emr.slgi.chatjoin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.status(401).body("업데이트 실패");
		}
	}
	
	
	@PutMapping("/updateOutTime")
	public ResponseEntity<Object> updateOutTime(@RequestBody ChatJoin join){
		boolean result = chatJoinService.updateOutTime(join);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(401).body("업데이트 실패");
		}
	}	
	
	

}
