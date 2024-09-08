package com.SpringBootREST.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.SpringBootREST.data.vo.BookVO;
import com.SpringBootREST.model.Book;

public class MockBook {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Book mockEntity() throws ParseException {
		return  mockEntity(0);
	}
	
	public BookVO mockVO() throws ParseException {
		return mockVO(0);
	}
	
	public Book mockEntity(Integer number) throws ParseException {
		Book book = new Book();
		book.setId(number.longValue());
		book.setAuthor("Test Author" + number);
		book.setLaunchDate(sdf.parse("01/01/2001"));
		book.setPrice(5.00);
		book.setTitle("Test Title" + number);
		return book;
	}
	
	public BookVO mockVO(Integer number) throws ParseException {
		BookVO vo = new BookVO();
		vo.setKey(number.longValue());
		vo.setAuthor("Test Author" + number);
		vo.setLaunchDate(sdf.parse("01/01/2001"));
		vo.setPrice(5.00);
		vo.setTitle("Test Title" + number);
		return vo;
		
	}
	
	public List<Book> mockEntityList() throws ParseException{
		List<Book> books = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}
	
	public List<BookVO> mockVoList() throws ParseException{
		List<BookVO> books = new ArrayList<>();
		for(int i = 0; i<5; i++) {
			books.add(mockVO(i));
		}
		return books;
	}
}
