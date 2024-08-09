package com.wchamara.spring6webapp.bootstrap;

import com.wchamara.spring6webapp.domain.Author;
import com.wchamara.spring6webapp.domain.Book;
import com.wchamara.spring6webapp.domain.Publisher;
import com.wchamara.spring6webapp.repository.AuthorRepository;
import com.wchamara.spring6webapp.repository.BookRepository;
import com.wchamara.spring6webapp.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
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

        Publisher publisher = new Publisher();
        publisher.setPublisherName("SFG Publishing");
        publisher.setAddress("St. Petersburg");
        publisher.setCity("St. Petersburg");
        publisher.setState("FL");
        publisher.setZipCode("33701");
        Publisher savedPublisher = publisherRepository.save(publisher);

        savedPublisher.getBooks().add(ddd);
        savedPublisher.getBooks().add(noEJB);

        publisherRepository.save(savedPublisher);

        System.out.println("Started in Bootstrap");

        Author ericSaved = authorRepository.save(eric);
        Author rodSaved = authorRepository.save(rod);

        Book dddSaved = bookRepository.save(ddd);
        Book noEJBSaved = bookRepository.save(noEJB);

        dddSaved.setPublisher(savedPublisher);
        noEJBSaved.setPublisher(savedPublisher);

        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);

        ericSaved.getBooks().add(noEJBSaved);
        noEJBSaved.getAuthors().add(ericSaved);

        rodSaved.getBooks().add(dddSaved);
        dddSaved.getAuthors().add(rodSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);
        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);

        System.out.println("Authors: " + authorRepository.count());
        System.out.println("Books: " + bookRepository.count());
        System.out.println("Publisher: " + publisherRepository.count());


    }

}
