package com.SpringBootREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBootREST.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
