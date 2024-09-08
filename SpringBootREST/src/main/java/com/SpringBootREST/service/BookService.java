package com.SpringBootREST.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBootREST.controllers.BookController;
import com.SpringBootREST.data.vo.BookVO;
import com.SpringBootREST.exceptions.IdNotNullException;
import com.SpringBootREST.exceptions.NullEntityException;
import com.SpringBootREST.exceptions.ResourceNotFoundException;
import com.SpringBootREST.mapper.DozerMapper;
import com.SpringBootREST.model.Book;
import com.SpringBootREST.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public BookVO findById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		BookVO vo = DozerMapper.parseObject(book, BookVO.class);
		vo.add(linkTo((methodOn(BookController.class)).findById(id)).withSelfRel());
		return vo;
	}
	
	public List<BookVO> findAll(){
		List<Book> books = bookRepository.findAll();
		if(books == null) {
			throw new ResourceNotFoundException("Not Found");
		}
		List<BookVO> vos = DozerMapper.parseListObject(books, BookVO.class);
		vos.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return vos;
	}
	
	public BookVO create(BookVO bookVO) {
		if(bookVO == null) {
			throw new  NullEntityException();
		}
		Book book = DozerMapper.parseObject(bookVO, Book.class);
		BookVO vo = DozerMapper.parseObject(bookRepository.save(book), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO bookVO) {
		if(bookVO.getKey() == null) {
			throw new  IdNotNullException();
		}
		Book book = bookRepository.findById(bookVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		book.setAuthor(bookVO.getAuthor());
		book.setLaunchDate(bookVO.getLaunchDate());
		book.setPrice(bookVO.getPrice());
		book.setTitle(bookVO.getTitle());
		BookVO vo = DozerMapper.parseObject(bookRepository.save(book), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		bookRepository.delete(book);
	}

}
