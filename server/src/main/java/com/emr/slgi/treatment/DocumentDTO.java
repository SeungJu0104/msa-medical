package com.emr.slgi.treatment;


import lombok.Data;

@Data
public class DocumentDTO {
    private String prescriptionCode;
    private int volume;
    private String medicineName;
    private String treatDate;
    private String patientName;
    private String rrn;
    private String patientUuid;
    private String doctorName;
    private String diseaseName;
    private String diseaseCode;
}
