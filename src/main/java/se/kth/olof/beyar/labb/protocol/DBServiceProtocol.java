package se.kth.olof.beyar.labb.protocol;

import se.kth.olof.beyar.labb.model.Author;
import se.kth.olof.beyar.labb.model.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBServiceProtocol
{
    ArrayList<Book> findByAuthor(String name) throws SQLException;

    ArrayList<Book> findByISBN(String isbn) throws SQLException;

    ArrayList<Book> findByTitle(String title) throws SQLException;

    ArrayList<Book> findByGenre(String genre) throws SQLException;

    ArrayList<Book> findByRating(String rating) throws SQLException;

    void insertBook(Book book) throws SQLException;

    void insertAuthor(Author author) throws SQLException;

    void insertWrittenBy(String bookISBN, String authorSSN) throws SQLException;

    void insertBookByAuthor(Author author, Book book) throws SQLException;

    void insertBookUpdateAuthor(Book book, String authorSSN) throws SQLException;

    void insertAuthorUpdateBook(Author author, String bookISBN) throws SQLException;
}
