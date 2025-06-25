package com.emr.slgi.chat.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.chat.DTO.ChatRoom;

@Mapper
public interface ChatRoomDAO {

	public List<ChatRoom> getList(String uuid);

	public void insert(ChatRoom room);

	public String loadChatName(int roomId);

	public List<String> getUuid(Integer roomId);
}
