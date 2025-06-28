package com.emr.slgi.diagnosis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiagnosisDAO {

	public void insertDiagnosis(Diagnosis diagnosis);

	public List<Diagnosis> selectDiagnosis(int id);

}
