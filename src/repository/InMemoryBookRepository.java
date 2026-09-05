package repository;

import exceptions.BookNotFoundException;
import models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books;
    public InMemoryBookRepository() {
        books = new HashMap<>();
    }


    @Override
    public void addBook(Book book) {
        // create a new copy
        Book newBook = new Book(book);
        books.put(book.getIsbn(), newBook);
    }

    @Override
    public void removeBook(String isbn) throws BookNotFoundException {
        Book removedBook = books.remove(isbn);
        if(removedBook == null) {
            throw new BookNotFoundException(isbn);
        }
    }

    @Override
    public void updateBook(Book book) throws BookNotFoundException {
        Book matchingBook = books.get(book.getIsbn());
        if(matchingBook == null) {
            throw new BookNotFoundException(book.getIsbn());
        }
        matchingBook.setTitle(book.getTitle());
        matchingBook.setAuthor(book.getAuthor());
        matchingBook.setPublicationYear(book.getPublicationYear());
    }

    @Override
    public Book findByISBN(String isbn) throws BookNotFoundException {
        Book book = books.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }
        return new Book(book);
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> booksByTitle = new ArrayList<>();
        for(Book book : books.values()) {
            if(book.getTitle().equals(title)) {
                booksByTitle.add(new Book(book));
            }
        }
        return booksByTitle;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for(Book book : books.values()) {
            if(book.getAuthor().equals(author)) {
                booksByAuthor.add(new Book(book));
            }
        }
        return booksByAuthor;
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        for(Book book : books.values()) {
            allBooks.add(new Book(book));
        }
        return allBooks;
    }
}
