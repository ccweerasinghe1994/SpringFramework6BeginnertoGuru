package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a book entity.
 * This class is mapped to a database table using JPA annotations.
 */
@Entity
public class Book {

    /**
     * The unique identifier for the book.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The ISBN of the book.
     */
    private String isbn;

    /**
     * The set of authors associated with the book.
     * This relationship is managed by the Author entity.
     */
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    /**
     * Gets the unique identifier for the book.
     * @return the unique identifier for the book.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     * @param id the unique identifier for the book.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the ISBN of the book.
     * @return the ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn the ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the set of authors associated with the book.
     * @return the set of authors associated with the book.
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the set of authors associated with the book.
     * @param authors the set of authors associated with the book.
     */
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    /**
     * Checks if this book is equal to another object.
     * @param o the object to compare with.
     * @return true if this book is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && Objects.equals(authors, book.authors);
    }

    /**
     * Generates a hash code for this book.
     * @return the hash code for this book.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, authors);
    }
}