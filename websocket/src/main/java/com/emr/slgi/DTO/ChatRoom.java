package com.emr.slgi.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	private Integer roomId;
	private String roomName;
	private LocalDate createDate;
	private String uuid;
	private String content;
	private int count ; 
	private LocalDateTime lastMessageTime;
	
}
