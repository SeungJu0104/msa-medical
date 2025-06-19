package com.emr.slgi.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {
	
	private final ChatMessageService chatMessageService;
	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/message")
	public void message(ChatMessage content) {
		try {
			System.out.println(">> 서버 수신 메시지: " + content);
			
			chatMessageService.saveMessage(content);
			messagingTemplate.convertAndSend("/sub/chat/room/" + content.getRoomId(), content);

		} catch (Exception e) {
			System.out.println(">>> 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}



}
