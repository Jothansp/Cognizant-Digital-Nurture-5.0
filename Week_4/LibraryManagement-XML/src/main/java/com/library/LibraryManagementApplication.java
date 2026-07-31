package com.library;

import com.library.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Ex 1 / 2 / 3 / 5 / 7 / 8 driver.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {

            BookService service = context.getBean("bookService", BookService.class);
            System.out.println("Catalogue: " + service.getCatalogueName());

            service.registerBook("The Pragmatic Programmer");
            service.registerBook("Clean Code");

            System.out.println("All books: " + service.listBooks());
        }
    }
}
