package com.emr.slgi.chat.DTO;

import java.time.LocalDateTime;

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
	private String name;
	private LocalDateTime createDate;
}