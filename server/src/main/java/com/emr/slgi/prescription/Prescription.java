package com.emr.slgi.prescription;

import lombok.Data;

@Data
public class Prescription {
	private String code;
	private Integer treatmentId;
	private int volume;
	private String name;
	private Integer timesPerDay;
	private Integer perDay ;
	private String instructions;
}
