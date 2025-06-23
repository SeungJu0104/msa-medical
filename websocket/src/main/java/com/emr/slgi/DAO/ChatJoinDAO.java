package com.emr.slgi.chatjoin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatJoinDAO {

	 void insert(ChatJoin join);

	 public int updateJoinTime(ChatJoin join);

	public int updateOutTime(ChatJoin join);

}
