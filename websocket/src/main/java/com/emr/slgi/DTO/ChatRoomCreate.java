package com.emr.slgi.chatroom;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomCreate {
	private String roomName;
    private String uuid;
    private List<String> members;
}
