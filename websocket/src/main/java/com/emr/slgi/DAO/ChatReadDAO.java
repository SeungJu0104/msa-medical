package com.emr.slgi.read;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatReadDAO {
	
	public void readtime(ChatRead read);

	public void joinreadtime(ChatRead read);

	public List<ChatRead> selectMessages(ChatRead read);

	public List<ChatAlarmDTO> getList(String uuid);
}
