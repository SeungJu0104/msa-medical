package com.emr.slgi.payment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMentDAO {

	public void insertPayMent(int id);

	public List<PayMent> getList();

	public int statusPayment(int id);
}
