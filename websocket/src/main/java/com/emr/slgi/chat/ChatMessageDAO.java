package com.emr.slgi.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageDAO {

	public List<ChatMessage> getList(String roomId);

	public void saveMessage(ChatMessage content);

}
