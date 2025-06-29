package com.emr.slgi.treatment;

import java.util.List;

import com.emr.slgi.attachment.Attachment;
import com.emr.slgi.diagnosis.Diagnosis;
import com.emr.slgi.prescription.Prescription;

import lombok.Data;

@Data
public class TotalTreatmentDTO {
	 private Treatment treatment;
	 private List<Prescription> prescriptions;
	 private List<Diagnosis> diagnosis;
	 private List<Attachment> attachments;
}
