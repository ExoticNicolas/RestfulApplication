package com.SpringBootREST.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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
	
	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;

	public BookVO findById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		BookVO vo = DozerMapper.parseObject(book, BookVO.class);
		vo.add(linkTo((methodOn(BookController.class)).findById(id)).withSelfRel());
		return vo;
	}

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable){
		
		var booksPage = bookRepository.findAll(pageable);
		
		var booksVOPage = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		booksVOPage.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(booksVOPage, link);
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
