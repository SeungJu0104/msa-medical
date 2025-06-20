package com.emr.slgi.chatroom;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emr.slgi.chatjoin.ChatJoinDAO;
import com.emr.slgi.chatjoin.ChatJoin;

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
	

}
