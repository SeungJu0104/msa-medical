package com.emr.slgi.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.emr.slgi.DTO.ChatAlarmDTO;
import com.emr.slgi.DTO.ChatMessage;
import com.emr.slgi.DTO.ChatRoom;
import com.emr.slgi.service.ChatMessageService;
import com.emr.slgi.service.ChatReadService;
import com.emr.slgi.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
	
	private final ChatMessageService chatMessageService;
	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatRoomService chatRoomService;
	private final ChatReadService chatReadService;

	@MessageMapping("/chat/message")
	public void message(ChatMessage content) {
		chatMessageService.saveMessage(content);
		messagingTemplate.convertAndSend("/sub/chatroom/" + content.getRoomId(), content);
		List<String> uuids = chatRoomService.getUuid(content.getRoomId());
		 for(String uu : uuids) {
			 List<ChatRoom> list = chatRoomService.getList(uu);
			 messagingTemplate.convertAndSend("/sub/chatrooms/" + uu, list);
			 List<ChatAlarmDTO> alarmList = chatReadService.getList(uu);
			 messagingTemplate.convertAndSend("/sub/alarms/"+uu,alarmList);
		 }
	}
	@MessageMapping("/chat/list")
	public void list(ChatRoom load) {
		 List<String> uuids = chatRoomService.getUuid(load.getRoomId());
		 for(String uu : uuids) {
			 List<ChatRoom> list = chatRoomService.getList(uu);
			  messagingTemplate.convertAndSend("/sub/chatrooms/" + uu, list);
		 }
	}
	@MessageExceptionHandler(Exception.class)
	public void handleException(Exception e) {
	    log.error("WebSocket 에러 발생: {}", e.getMessage());
	    messagingTemplate.convertAndSend("/sub/errors", "에러 발생: " + e.getMessage());

	}
	
}
