package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Ex 1 / Ex 5: Data-access component managed by the Spring container.
 */
public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        System.out.println("[BookRepository] instance created by Spring container.");
    }

    public void addBook(String title) {
        books.add(title);
        System.out.println("[BookRepository] Persisted book: " + title);
    }

    public List<String> findAll() {
        return books;
    }
}
