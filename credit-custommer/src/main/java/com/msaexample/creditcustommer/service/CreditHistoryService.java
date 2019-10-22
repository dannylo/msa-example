package com.msaexample.creditcustommer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msaexample.creditcustommer.domain.CreditHistory;
import com.msaexample.creditcustommer.repository.CreditHistoryRepository;

@Service
public class CreditHistoryService {
	
	@Autowired
	private CreditHistoryRepository repository;
	
	public CreditHistory save(CreditHistory creditHistory) {
		return this.repository.save(creditHistory);
	}

}
