package com.emr.slgi.treatment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TreatmentDAO {

	public int insertTreatment(Treatment treatment);

	public List<Treatment> getHistory(Treatment treatment);

	public Treatment selectTreatment(int id);

}
