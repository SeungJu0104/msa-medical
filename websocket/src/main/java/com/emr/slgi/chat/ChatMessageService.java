package com.emr.slgi.chat;

import java.util.List;

import org.springframework.stereotype.Service;

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
