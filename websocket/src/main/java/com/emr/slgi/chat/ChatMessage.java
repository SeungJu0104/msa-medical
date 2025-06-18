package com.emr.slgi.chat;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

	private String messageId;
	private String roomId;
	private String uuid; 
	private String content;
	private LocalDate createDate;
	
}
