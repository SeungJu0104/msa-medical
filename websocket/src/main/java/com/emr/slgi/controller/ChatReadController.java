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
	
	
	//실시간 채팅시에 읽음표시
	@PostMapping("/readtime")
	public ResponseEntity<Object> readtime(@RequestBody ChatRead read){
		int result = chatReadService.readtime(read);
		if (result < 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅 읽음처리를 못했습니다");
		}
		return  ResponseEntity.ok().build();
	}
	// 채팅방 입장시에 안읽은 메세지 읽음처리
	@PostMapping("/joinreadtime")
	public ResponseEntity<Object> joinreadtime(@RequestBody ChatRead read) {
		int result= chatReadService.selectMessages(read);
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
