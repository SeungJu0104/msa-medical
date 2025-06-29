package com.emr.slgi.prescription;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrescriptionDAO {

	public void insertPrescription(Prescription p);

	public List<Prescription> selectPrescriptions(int id);

}
