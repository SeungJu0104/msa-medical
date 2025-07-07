package com.emr.slgi.payment;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
	private final PaymentService paymentService;
	
	@PostMapping("/paymentList")
	public ResponseEntity<Object> paymentList (PayMent payMent){
		List<PayMent> list = paymentService.getList();
		
		return ResponseEntity.ok(Map.of("list",list));
	}
	
	
	@GetMapping("statusPayment/{id}")
	public ResponseEntity<Object> statusPayment(@PathVariable("id") int id){
		paymentService.statusPayment(id);
		return ResponseEntity.ok().build();
	}
}
