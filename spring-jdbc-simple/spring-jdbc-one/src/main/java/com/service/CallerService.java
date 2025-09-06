package com.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CallerService {
	
	@Value("${key}")
	private String mesg;
	
	public void showMessage() {
		System.out.println("Message on board: "+mesg);
	}

}
