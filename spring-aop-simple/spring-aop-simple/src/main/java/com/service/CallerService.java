package com.service;

import org.springframework.stereotype.Service;

@Service
public class CallerService {

	public String sayHello(String name) {		
		if(name.equals("user"))
			throw new RuntimeException("Name not valid");
		
        return "Hello, " + name;
    }
	
	public void say() {
		//return "idle";
		try {
			Thread.sleep(2000);
		}catch(Exception e) {}
	}
	
	public int sayTotal(int a, int b, int c) {
		return a+b+c;
	}
	
	public int sayTotal(int...nums) {
		int sum=0;
		for(int n:nums)
			sum+=n;
		
		return sum;
	}
	
	

}
