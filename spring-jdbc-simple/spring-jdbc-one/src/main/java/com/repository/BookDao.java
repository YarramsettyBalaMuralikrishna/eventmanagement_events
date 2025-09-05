package com.repository;

import java.util.List;

import com.model.Book;

public interface BookDao {
	int save(Book book);
    Book findById(int id);
    List<Book> findAll();
    int update(Book book);
    int deleteById(int id);
    void updateAllBookPrices(long... ids);
}
