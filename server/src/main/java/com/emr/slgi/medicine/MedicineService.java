package com.emr.slgi.medicine;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineService {
	private final MedicineDAO medicineDAO;
	public List<Medicine> getKeyword(String keyword) {
		
		return medicineDAO.getKeyword(keyword);
	}

}
