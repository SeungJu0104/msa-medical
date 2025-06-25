package com.emr.slgi.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emr.slgi.chat.DAO.ChatReadDAO;
import com.emr.slgi.chat.DTO.ChatAlarmDTO;
import com.emr.slgi.chat.DTO.ChatRead;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatReadService {
	private final ChatReadDAO chatReadDAO;
	
	@Transactional
	public int selectMessages(ChatRead read) {
		int result = 0;
		List<ChatRead>	list = chatReadDAO.selectMessages(read); 
		for (ChatRead msg : list) {
	        result += chatReadDAO.readtime(msg);
	    }
		return result;
	}

	public List<ChatAlarmDTO> getList(String uuid) {
		return chatReadDAO.getList(uuid);
	}
}