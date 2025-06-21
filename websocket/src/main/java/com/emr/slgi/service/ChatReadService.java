package com.emr.slgi.read;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
