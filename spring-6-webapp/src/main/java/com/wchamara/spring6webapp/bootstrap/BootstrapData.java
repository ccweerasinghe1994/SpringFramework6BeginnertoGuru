package com.wchamara.spring6webapp.bootstrap;

import com.wchamara.spring6webapp.domain.Author;
import com.wchamara.spring6webapp.domain.Book;
import com.wchamara.spring6webapp.repository.AuthorRepository;
import com.wchamara.spring6webapp.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");



        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("1234");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("2345");




        System.out.println("Started in Bootstrap");

        Author ericSaved = authorRepository.save(eric);
        Author rodSaved = authorRepository.save(rod);

        Book dddSaved = bookRepository.save(ddd);
        Book noEJBSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(noEJBSaved);
        rodSaved.getBooks().add(dddSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);

        System.out.println("Authors: " + authorRepository.count());
        System.out.println("Books: " + bookRepository.count());


    }

}
