package com.emr.slgi.chatroom;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomDAO chatRoomDAO;

	public List<ChatRoom> getList(String uuid) {
		
		// TODO Auto-generated method stub
		return chatRoomDAO.getList(uuid);
	}

}
