package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Ex 9: Spring Data JPA repository - CRUD methods provided out of the box.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
