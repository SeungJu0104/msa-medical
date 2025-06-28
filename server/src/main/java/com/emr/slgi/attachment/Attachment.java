package com.emr.slgi.attachment;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Attachment {
	private int id;
	private int treatmentId; 
	private String originalName ;
	private String fileName ;
	private String path ;
	private int size ;
	private String contentType;
	private String extension;
	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;
	private LocalDateTime deleteDateTime;
	

}
