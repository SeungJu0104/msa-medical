package com.emr.slgi.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emr.slgi.chat.DAO.ChatJoinDAO;
import com.emr.slgi.chat.DAO.ChatRoomDAO;
import com.emr.slgi.chat.DTO.ChatJoin;
import com.emr.slgi.chat.DTO.ChatRoom;
import com.emr.slgi.chat.DTO.ChatRoomCreate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomDAO chatRoomDAO;
	private final ChatJoinDAO chatJoinDAO;

	public List<ChatRoom> getList(String uuid) {
		return chatRoomDAO.getList(uuid);
	}
	
	@Transactional
	public int createChat(ChatRoomCreate data) {
	    ChatRoom room = new ChatRoom();
	    room.setRoomName(data.getRoomName());

	    chatRoomDAO.insert(room); 
	    
	    int roomId = room.getRoomId();
	    
	    for (String uuid : data.getMembers()) {
	        ChatJoin join = new ChatJoin();
	        join.setRoomId(roomId);
	        join.setUuid(uuid);
	        chatJoinDAO.insert(join);
	    }
	    return roomId;
	    }
	

	public String loadChatName(int roomId) {
		return chatRoomDAO.loadChatName(roomId);
	}


	public List<String> getUuid(Integer roomId) {
		return chatRoomDAO.getUuid(roomId);
	}


}
