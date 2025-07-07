package com.emr.slgi.payment;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMentDAO {

	public void insertPayMent(int id);
}
