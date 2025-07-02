package com.emr.slgi.chat.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.emr.slgi.chat.DTO.ChatJoin;

@Mapper
public interface ChatJoinDAO {

	public void insert(ChatJoin join);

	public int updateJoinTime(@Param("roomId") String roomId,  @Param("uuid") String uuid);

	public int updateOutTime(@Param("roomId") String roomId,  @Param("uuid") String uuid);


}