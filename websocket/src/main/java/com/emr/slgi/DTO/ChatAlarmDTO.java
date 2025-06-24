package com.emr.slgi.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatAlarmDTO {
	private Integer roomId;
	private String roomName;
	private String content;
	private int alarmCount;

}
