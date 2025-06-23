package com.emr.slgi.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRead {
	private Integer roomId;
	private String uuid;
	private Integer messageId;
	private LocalDateTime readTime;
// 24 c d 3
}