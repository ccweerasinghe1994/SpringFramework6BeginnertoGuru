package com.wchamara.spring6webapp.repository;

import com.wchamara.spring6webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
