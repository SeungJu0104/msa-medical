package com.emr.slgi.medicine;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicineDAO {

	public List<Medicine> getKeyword(String keyword);	
		
	

}
