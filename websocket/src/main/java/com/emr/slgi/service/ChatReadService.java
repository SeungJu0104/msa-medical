package com.emr.slgi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emr.slgi.read.ChatAlarmDTO;
import com.emr.slgi.read.ChatRead;
import com.emr.slgi.read.ChatReadDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatReadService {
	private final ChatReadDAO chatReadDAO;
	
	@Transactional
	public void selectMessages(ChatRead read) {
		
		List<ChatRead>	list = chatReadDAO.selectMessages(read); 
		for (ChatRead msg : list) {
	        chatReadDAO.readtime(msg);
	    }
	}

	public List<ChatAlarmDTO> getList(String uuid) {
		return chatReadDAO.getList(uuid);
	}
}
