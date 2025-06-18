package com.emr.slgi.chatroom;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	private int roomId;
	private String roomName;
	private LocalDate createDate;
	
}
