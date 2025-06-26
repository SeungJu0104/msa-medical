package com.emr.slgi.disease;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiseaseDAO {

	public List<Disease> getKeyword(String keyword);	
		
	

}
