package com.emr.slgi.chat.controller;


import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.emr.slgi.chat.DTO.ChatMessage;
import com.emr.slgi.chat.DTO.ChatRoom;
import com.emr.slgi.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
	
	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatService chatService;

	@MessageMapping("/chat/message")
	public void message(ChatMessage content) {
		chatService.getUuid(content);
		chatService.getRoomId(content);
		
	}
	@MessageMapping("/chat/list")
	public void list(ChatRoom load) {
		chatService.getChatList(load);
		 
	}
	@MessageExceptionHandler(Exception.class)
	public void handleingException(Exception e) {
	    log.error("WebSocket 에러 발생: {}", e.getMessage());
	    messagingTemplate.convertAndSend("/sub/errors", "에러 발생: " + e.getMessage());
	}
}
