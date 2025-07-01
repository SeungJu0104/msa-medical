package com.emr.slgi.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.chat.DTO.ChatMessage;
import com.emr.slgi.chat.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatmessage")
public class ChatMessageController {
	private final ChatMessageService chatMessageService;
	
	@GetMapping("/messageList/{roomId}")
	public ResponseEntity<Object> messageList(@PathVariable("roomId") String roomId) {
		List<ChatMessage> messageList = chatMessageService.getList(roomId);
		return ResponseEntity.ok(messageList);
	}
}
