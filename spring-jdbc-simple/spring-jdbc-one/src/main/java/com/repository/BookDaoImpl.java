package com.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Book;

@Repository
public class BookDaoImpl implements BookDao{

	    private final JdbcTemplate jdbcTemplate;

	    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	    @Override
	    public int save(Book book) {
	    	String sql="INSERT INTO books(title,author,price) values(?,?,?)";
	    	int rowsAffected=jdbcTemplate.update(sql,book.getTitle(),book.getAuthor(),book.getPrice());
	    	return rowsAffected;
	    }

	    @Override
	    public Book findById(int id) {
	    	String sql="SELECT * FROM books WHERE id=?";
	    	Book book_=jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Book.class),id);
	    	return book_;
	    }

	    @Override
	    public List<Book> findAll() {
	    	String sql="SELECT * FROM books";
	    	List<Book> books=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class));
	    	return books;
	    }

	    @Override
	    public int update(Book book) {
	    	String sql="UPDATE books set title=?,author=?,price=? where id=?";
	    	int rowsUpdated=jdbcTemplate.update(sql,book.getTitle(),book.getAuthor(), book.getPrice(),book.getId());
	        return rowsUpdated;
	    }

	    @Override
	    public int deleteById(int id) {	    	
	       //try out here
	       return 0;
	    }
	    
	    @Transactional
	    public void updateAllBookPrices(long... ids) {
	        for (long id : ids) {
	            jdbcTemplate.update("UPDATE books SET price = price + 100 WHERE id = ?", id);
	            int x = 1 / 0; // Exception!
	        }
	        System.out.println("Prices updated.");
	    }

}
