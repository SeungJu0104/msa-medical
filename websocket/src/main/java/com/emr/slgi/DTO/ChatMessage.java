package com.emr.slgi.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

	private Integer messageId;
	private Integer roomId;
	private String uuid; 
	private String content;
	private LocalDate createDate;
}