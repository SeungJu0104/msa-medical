package com.emr.slgi.controller;

import java.util.HashMap;
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

import com.emr.slgi.DTO.Member;
import com.emr.slgi.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Member id){
		Member member = memberService.getById(id.getUuid());
		if(member != null) {
			Map<String , Object> result = new HashMap<>();
			result.put("uuid", member.getUuid());
			return ResponseEntity.ok(result); 
		}
		else {
			return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다.");
		}
	}
	
	
	@GetMapping("/memberList/{uuid}")
	public ResponseEntity<Object> memberList(@PathVariable("uuid") String uuid){
		List<Member> memberList = memberService.getList(uuid);
		if (memberList== null || memberList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 리스트를 불러 오지 못했습니다");
		}
		
			return ResponseEntity.ok(memberList); 
		}
	
}
