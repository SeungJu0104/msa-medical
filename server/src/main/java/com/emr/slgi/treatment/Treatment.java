package com.emr.slgi.treatment;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Treatment {
	private int id ;
	private String uuid;
	private String treatContent;
	private LocalDate treatDate;
	private String writeYn;
}
