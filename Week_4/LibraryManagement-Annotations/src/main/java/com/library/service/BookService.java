package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ex 6: detected by component scanning via @Service.
 * Dependency injected by type through @Autowired constructor.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository injected via @Autowired constructor.");
    }

    public void registerBook(String title) {
        bookRepository.addBook(title);
    }

    public List<String> listBooks() {
        return bookRepository.findAll();
    }
}
