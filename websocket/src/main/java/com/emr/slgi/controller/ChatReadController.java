package com.emr.slgi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.DTO.ChatAlarmDTO;
import com.emr.slgi.DTO.ChatRead;
import com.emr.slgi.service.ChatReadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatread")
@Slf4j
public class ChatReadController {
	
	private final ChatReadService chatReadService;
	
	@PostMapping("/readtime")
	public ResponseEntity<Object> readtime(@RequestBody ChatRead read) {
		System.out.println(" 읽음 처리 요청: " + read);
		int result = chatReadService.selectMessages(read);
		
		if (result < 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅 읽음처리를 못했습니다");
		}
		return  ResponseEntity.ok().build();
		
	}
	
	@PostMapping("/joinreadtime")
	public ResponseEntity<Object> joinreadtime(@RequestBody ChatRead read) {
		System.out.println("읽음 처리 요청: " + read);

		int result= chatReadService.selectMessages(read);
		System.out.println("읽음 처리 결과 count = " + result);  // ✅ 추가
		
		if (result < 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅 읽음처리를 못했습니다");
		}
		return  ResponseEntity.ok().build();
	}
	
	@GetMapping("/chatReadList/{uuid}")
	public ResponseEntity<Object> chatReadList(@PathVariable("uuid") String uuid) {
		List<ChatAlarmDTO> chatReadList = chatReadService.getList(uuid);
		if(chatReadList == null || chatReadList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅알림 목록을 불러 오지 못했습니다");
		}
		return ResponseEntity.ok(chatReadList);
	}
	

}
