package com.library.service;

import com.library.repository.BookRepository;

import java.util.List;

/**
 * Ex 1 / Ex 5: Service component.
 * Ex 2: setter injection of BookRepository.
 * Ex 7: constructor injection (catalogueName) + setter injection (repository).
 */
public class BookService {

    private BookRepository bookRepository;
    private String catalogueName;

    public BookService() {
        System.out.println("[BookService] instance created (default constructor).");
    }

    // Ex 7: constructor injection
    public BookService(String catalogueName) {
        this.catalogueName = catalogueName;
        System.out.println("[BookService] instance created (constructor injection): " + catalogueName);
    }

    // Ex 2 & Ex 7: setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository injected via setter.");
    }

    public void registerBook(String title) {
        bookRepository.addBook(title);
    }

    public List<String> listBooks() {
        return bookRepository.findAll();
    }

    public String getCatalogueName() {
        return catalogueName;
    }
}
