package com.emr.slgi.service;

import org.springframework.stereotype.Service;

import com.emr.slgi.DAO.ChatJoinDAO;
import com.emr.slgi.DTO.ChatJoin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatJoinService {
	private final ChatJoinDAO chatJoinDAO;


	public boolean updateJoinTime(ChatJoin join) {
		int result = chatJoinDAO.updateJoinTime(join);
		return result > 0;
	}
	
	
	public boolean updateOutTime(ChatJoin join) {
		int result = chatJoinDAO.updateOutTime(join);
		return result > 0;
	}

}