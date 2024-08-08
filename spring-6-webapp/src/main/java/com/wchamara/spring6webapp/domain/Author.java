package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

/**
 * Represents an author entity.
 * This class is mapped to a database table using JPA annotations.
 */
@Entity
public class Author {

    /**
     * The unique identifier for the author.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The first name of the author.
     */
    private String firstName;

    /**
     * The last name of the author.
     */
    private String lastName;

    /**
     * The set of books associated with the author.
     * This relationship is managed by the Book entity.
     */
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    /**
     * Gets the set of books associated with the author.
     * @return the set of books associated with the author.
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Sets the set of books associated with the author.
     * @param books the set of books associated with the author.
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Gets the unique identifier for the author.
     * @return the unique identifier for the author.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the author.
     * @param id the unique identifier for the author.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the first name of the author.
     * @return the first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     * @param firstName the first name of the author.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the author.
     * @return the last name of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     * @param lastName the last name of the author.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Checks if this author is equal to another object.
     * @param o the object to compare with.
     * @return true if this author is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(books, author.books);
    }

    /**
     * Generates a hash code for this author.
     * @return the hash code for this author.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, books);
    }
}