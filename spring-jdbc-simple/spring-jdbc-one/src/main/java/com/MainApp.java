package com;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.config.AppConfig;
import com.model.Book;
import com.repository.BookDao;
import com.service.CallerService;

public class MainApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		CallerService service = ctx.getBean(CallerService.class);
		service.showMessage();

		BookDao bookDao = ctx.getBean(BookDao.class);
		/*
		 * int count=0; count+= bookDao.save(new Book("Book1","Author1",500.0)); count+=
		 * bookDao.save(new Book("Book2","Author3",350.0)); count+= bookDao.save(new
		 * Book("Book5","Author1",640.0));
		 * 
		 * count= bookDao.update(new Book(10,"Book1","Author1",610.0));
		 * System.out.println(count+ " books added/updated");
		 */
		/*try {
			System.out.println(bookDao.findById(10));
			System.out.println(bookDao.findById(100));// is not there
		} catch (Exception e) {
			System.out.println("Book not available");
		}*/
		
			//List<Book> books=bookDao.findAll();
			//books.forEach(System.out::println);// method reference ; same as b->Syso(b)
		
			bookDao.updateAllBookPrices(10,12,14,11);

		ctx.close();
	}

}
