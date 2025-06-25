package com.emr.slgi.chat.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.chat.DTO.ChatAlarmDTO;
import com.emr.slgi.chat.DTO.ChatRead;

@Mapper
public interface ChatReadDAO {
	
	public int readtime(ChatRead read);

	public void joinreadtime(ChatRead read);

	public List<ChatRead> selectMessages(ChatRead read);

	public List<ChatAlarmDTO> getList(String uuid);
}