package repository;

import exceptions.BookNotFoundException;
import models.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);
    void removeBook(String isbn) throws BookNotFoundException;
    void updateBook(Book book) throws BookNotFoundException;
    Book findByISBN(String isbn) throws BookNotFoundException;
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findAllBooks();
}
