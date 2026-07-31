package com.library.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Ex 6: detected by component scanning via @Repository.
 */
@Repository
public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public void addBook(String title) {
        books.add(title);
        System.out.println("[BookRepository] Persisted book: " + title);
    }

    public List<String> findAll() {
        return books;
    }
}
