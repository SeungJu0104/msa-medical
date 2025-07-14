package com.emr.slgi.payment;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PayMent {
	private	int id;
	private	int payment;
	private int treatmentId;
	private String patientName ;
	private LocalDateTime paymentDate;
	
}