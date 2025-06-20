package com.emr.slgi.chatroom;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	
	
	@GetMapping("/list/{uuid}")
	public ResponseEntity<Object> list(@PathVariable("uuid") String uuid){
		List<ChatRoom> list = chatRoomService.getList(uuid);
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody ChatRoomCreate data){
		 int roomId = chatRoomService.createChat(data);
		return ResponseEntity.ok(Map.of("roomId",roomId));
	}
}
