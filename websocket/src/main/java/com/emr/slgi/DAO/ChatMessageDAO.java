package com.emr.slgi.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.DTO.ChatMessage;

@Mapper
public interface ChatMessageDAO {

	public List<ChatMessage> getList(String roomId);

	public void saveMessage(ChatMessage content);

}