package com.emr.slgi.member;

import java.util.HashMap;
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
	
	
	@GetMapping("/list/{uuid}")
	public ResponseEntity<Object> list(@PathVariable("uuid") String uuid){
		List<Member> list = memberService.getList(uuid);
		
			return ResponseEntity.ok(list); 
		}
		
	
}
