package com.wchamara.spring6webapp.service;

import com.wchamara.spring6webapp.domain.Book;

public interface BookService {

    Iterable<Book> findAll();
}
