package com.emr.slgi.payment;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PayMentDAO payMentDAO;

	public List<PayMent> getList() {
		return payMentDAO.getList();
	}

	public int statusPayment(int id) {
		return payMentDAO.statusPayment(id);
		
	}

}
