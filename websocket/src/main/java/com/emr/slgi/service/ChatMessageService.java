package com.emr.slgi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emr.slgi.DAO.ChatMessageDAO;
import com.emr.slgi.DTO.ChatMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageDAO chatMessageDAO;

	public List<ChatMessage> getList(String roomId) {
		return chatMessageDAO.getList(roomId);
	}
	
	
	public void saveMessage(ChatMessage content) {
		chatMessageDAO.saveMessage(content);
		
		
	}

}