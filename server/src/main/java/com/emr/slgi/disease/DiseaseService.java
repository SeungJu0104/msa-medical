package com.emr.slgi.disease;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiseaseService {
	private final DiseaseDAO diseaseDAO;
	public List<Disease> getKeyword(String keyword) {
		
		return diseaseDAO.getKeyword(keyword);
	}

}
