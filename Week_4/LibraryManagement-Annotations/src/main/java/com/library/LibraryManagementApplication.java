package com.library;

import com.library.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Ex 6 driver: verifies annotation-based configuration.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {

            BookService service = context.getBean(BookService.class);
            service.registerBook("Effective Java");
            service.registerBook("Spring in Action");

            System.out.println("All books: " + service.listBooks());
        }
    }
}
