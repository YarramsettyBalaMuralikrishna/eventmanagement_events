package com;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.config.AppConfig;
import com.service.CallerService;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		CallerService service = context.getBean(CallerService.class);

		try {
			//sayNothing()
			service.say();
			
			// Successful call
			System.out.println("Result: " + service.sayHello("Soma"));

			// Exception call
			System.out.println("Result: " + service.sayHello("user"));
			
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
			service.sayTotal(1, 2, 3);
			service.sayTotal(1, 2, 3,4,5,6);
			service.sayTotal(5);

		context.close();
	}

}
