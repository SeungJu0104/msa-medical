package com.emr.slgi.read;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.service.ChatReadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/read")
@Slf4j
public class ChatReadController {
	
	private final ChatReadService chatReadService;
	
	@PostMapping("/readtime")
	public ResponseEntity<Object> readtime(@RequestBody ChatRead read) {
		System.out.println(" 읽음 처리 요청: " + read);

		log.info("room = {}" , read.getRoomId());
		log.info("message = {}" , read.getMessageId());
		log.info("uuid = {}" , read.getUuid());
		chatReadService.selectMessages(read);
		
		return ResponseEntity.ok().build();
		
	}
	
	
	@PostMapping("/joinreadtime")
	public ResponseEntity<Object> joinreadtime(@RequestBody ChatRead read) {
		System.out.println("읽음 처리 요청: " + read);

		log.info("room = {}" , read.getRoomId());
		log.info("message = {}" , read.getMessageId());
		log.info("uuid = {}" , read.getUuid());
		chatReadService.selectMessages(read);
		
		return ResponseEntity.ok().build();	
	}
	
	@GetMapping("/list/{uuid}")
	public ResponseEntity<Object> list(@PathVariable("uuid") String uuid) {
		List<ChatAlarmDTO> result = chatReadService.getList(uuid);
		
		return ResponseEntity.ok(result);	
	}
	

}
