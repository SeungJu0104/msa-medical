package com.emr.slgi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emr.slgi.DAO.ChatReadDAO;
import com.emr.slgi.DTO.ChatAlarmDTO;
import com.emr.slgi.DTO.ChatRead;

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

	public int readtime(ChatRead read) {
		int result = chatReadDAO.readtime(read);
		return result;
	}
}