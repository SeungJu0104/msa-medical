package com.emr.slgi.chatjoin;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatJoin {
	private Integer roomId;
	private String uuid;
	private LocalDateTime joinTime;
	private LocalDateTime outTime;
	
}
