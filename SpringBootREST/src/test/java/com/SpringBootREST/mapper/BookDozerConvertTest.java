package com.SpringBootREST.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.SpringBootREST.data.vo.BookVO;
import com.SpringBootREST.model.Book;

public class BookDozerConvertTest {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private MockBook inputObject;
	
	@BeforeEach
	public void setUp() {
		inputObject = new MockBook();
	}
	
	@Test
	public void convertVOToEntity() throws ParseException {
		Book book = DozerMapper.parseObject(inputObject.mockVO(), Book.class);
		assertEquals(Long.valueOf(0),book.getId());
		assertEquals("Test Author0", book.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), book.getLaunchDate());
		assertEquals(5.00, book.getPrice());
		assertEquals("Test Title0", book.getTitle());
	}

	@Test
	public void convertEntityToVO() throws ParseException {
		BookVO vo = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
		assertEquals(Long.valueOf(0),vo.getKey());
		assertEquals("Test Author0", vo.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), vo.getLaunchDate());
		assertEquals(5.00, vo.getPrice());
		assertEquals("Test Title0", vo.getTitle());
	}
	
	@Test
	public void convertListEntityToListVO() throws ParseException {
		List<BookVO> listVO = DozerMapper.parseListObject(inputObject.mockEntityList(), BookVO.class);
		
		BookVO vo0 = listVO.get(0);
		
		assertEquals(Long.valueOf(0),vo0.getKey());
		assertEquals("Test Author0", vo0.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), vo0.getLaunchDate());
		assertEquals(5.00, vo0.getPrice());
		assertEquals("Test Title0", vo0.getTitle());
		
		BookVO vo2 = listVO.get(2);
		
		assertEquals(Long.valueOf(2),vo2.getKey());
		assertEquals("Test Author2", vo2.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), vo2.getLaunchDate());
		assertEquals(5.00, vo2.getPrice());
		assertEquals("Test Title2", vo2.getTitle());
		
		BookVO vo4 = listVO.get(4);
		
		assertEquals(Long.valueOf(4),vo4.getKey());
		assertEquals("Test Author4", vo4.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), vo4.getLaunchDate());
		assertEquals(5.00, vo4.getPrice());
		assertEquals("Test Title4", vo4.getTitle());
	}
	
	@Test
	public void convertListVOToListEntity() throws ParseException {
		
		List<Book> list = DozerMapper.parseListObject(inputObject.mockVoList(), Book.class);
		
		Book book0 = list.get(0);
		
		assertEquals(Long.valueOf(0),book0.getId());
		assertEquals("Test Author0", book0.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), book0.getLaunchDate());
		assertEquals(5.00, book0.getPrice());
		assertEquals("Test Title0", book0.getTitle());
		
		Book book2 = list.get(2);
		
		assertEquals(Long.valueOf(2),book2.getId());
		assertEquals("Test Author2", book2.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), book2.getLaunchDate());
		assertEquals(5.00, book2.getPrice());
		assertEquals("Test Title2", book2.getTitle());
		Book book4 = list.get(4);
		
		assertEquals(Long.valueOf(4),book4.getId());
		assertEquals("Test Author4", book4.getAuthor());
		assertEquals(sdf.parse("01/01/2001"), book4.getLaunchDate());
		assertEquals(5.00, book4.getPrice());
		assertEquals("Test Title4", book4.getTitle());
	}
}
