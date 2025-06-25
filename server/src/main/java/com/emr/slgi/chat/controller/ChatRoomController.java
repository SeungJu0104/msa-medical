package com.emr.slgi.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.chat.DTO.ChatRoom;
import com.emr.slgi.chat.DTO.ChatRoomCreate;
import com.emr.slgi.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	
	@GetMapping("/chatRoomList/{uuid}")
	public ResponseEntity<Object> chatRoomList(@PathVariable("uuid") String uuid){
		List<ChatRoom> chatRoomList = chatRoomService.getList(uuid);
		return ResponseEntity.ok(chatRoomList);
	}
	
	
	@PostMapping("/createChatRoom")
	public ResponseEntity<Object> createChatRoom(@RequestBody ChatRoomCreate data){
		 int roomId = chatRoomService.createChat(data);
		 if(roomId < 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅방을 생성하지 못했습니다");
		 }
		return ResponseEntity.ok(Map.of("roomId",roomId));
	}
	
	@GetMapping("/loadChatName/{roomId}")
	public ResponseEntity<Object> loadChatName(@PathVariable("roomId")int roomId){
		String roomName = chatRoomService.loadChatName(roomId);
		if (roomName == null || roomName.isEmpty() ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("채팅방 이름을 가져오지 못했습니다");
		}
		return  ResponseEntity.ok(roomName);
	}

}
