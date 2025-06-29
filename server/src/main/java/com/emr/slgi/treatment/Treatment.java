package com.emr.slgi.treatment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Treatment {
	private int id ;
	private String patientUuid;
	private String doctorUuid;
	private String status;
	private String treatContent;
	private LocalDate treateDate;
	private LocalDateTime treatWriteDate;
	private LocalDateTime prescriptionDate;
	private int payment;
	private LocalDateTime paymentDate;
	
}
