package com.emr.slgi.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.DTO.ChatJoin;

@Mapper
public interface ChatJoinDAO {

	 void insert(ChatJoin join);

	 public int updateJoinTime(ChatJoin join);

	public int updateOutTime(ChatJoin join);

}