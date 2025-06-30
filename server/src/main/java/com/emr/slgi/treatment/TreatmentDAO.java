package com.emr.slgi.treatment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.emr.slgi.page.PageRequestDTO;

@Mapper
public interface TreatmentDAO {

	public int insertTreatment(Treatment treatment);

	public Treatment selectTreatment(int id);

	public int getHistoryCount(PageRequestDTO pageRequestDTO);

	public List<Treatment> getHistory(PageRequestDTO pageRequestDTO);


}
