package com.SpringBootREST.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBootREST.data.vo.BookVO;
import com.SpringBootREST.mapper.MockBook;
import com.SpringBootREST.model.Book;
import com.SpringBootREST.repositories.BookRepository;
import com.SpringBootREST.service.BookService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
	
	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@BeforeEach
	void setUpmocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
			
		}
	
	@Test
	void testFindById() throws ParseException {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Test Author1",result.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),result.getLaunchDate());
		assertEquals(5.00,result.getPrice());
		assertEquals("Test Title1",result.getTitle());
	}
	
	@Test
	void testFindAll() throws ParseException {
		
		List<Book> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var books = service.findAll();
		assertNotNull(books);
		assertEquals(5,books.size());
		
		var book1 = books.get(1);
		assertNotNull(book1);
		assertNotNull(book1.getKey());
		assertNotNull(book1.getLinks());
		assertTrue(book1.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Test Author1",book1.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),book1.getLaunchDate());
		assertEquals(5.00,book1.getPrice());
		assertEquals("Test Title1",book1.getTitle());
		
		var book2 = books.get(2);
		assertNotNull(book2);
		assertNotNull(book2.getKey());
		assertNotNull(book2.getLinks());
		assertTrue(book2.toString().contains("links: [</api/book/v1/2>;rel=\"self\"]"));
		assertEquals("Test Author2",book2.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),book2.getLaunchDate());
		assertEquals(5.00,book2.getPrice());
		assertEquals("Test Title2",book2.getTitle());
		
		var book4 = books.get(4);
		assertNotNull(book4);
		assertNotNull(book4.getKey());
		assertNotNull(book4.getLinks());
		assertTrue(book4.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Test Author4",book4.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),book4.getLaunchDate());
		assertEquals(5.00,book4.getPrice());
		assertEquals("Test Title4",book4.getTitle());
	}
	
	@Test
	void testCreate() throws ParseException {
		
		Book entity = input.mockEntity(1);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Test Author1",result.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),result.getLaunchDate());
		assertEquals(5.00,result.getPrice());
		assertEquals("Test Title1",result.getTitle());
		}
	
	@Test
	void testDelete() throws ParseException {
		
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}
	
	@Test
	void testUpdate() throws ParseException {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Test Author1",result.getAuthor());
		assertEquals(sdf.parse("01/01/2001"),result.getLaunchDate());
		assertEquals(5.00,result.getPrice());
		assertEquals("Test Title1",result.getTitle());
	}

}
