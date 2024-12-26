package se.kth.olof.beyar.labb.protocol;

import se.kth.olof.beyar.labb.model.Author;
import se.kth.olof.beyar.labb.model.Book;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DBServiceProtocol
{
    ArrayList<Book> findByAuthor(String query) throws SQLException;

    ArrayList<Book> findByISBN(String query) throws SQLException;

    ArrayList<Book> findByTitle(String query) throws SQLException;

    ArrayList<Book> findByGenre(String query) throws SQLException;

    ArrayList<Book> findByRating(String query) throws SQLException;

    void insertBook(Book book) throws SQLException;

    void insertAuthor(Author author)  throws SQLException;

    //TODO, gör till privat metod i MySQL implementationen
    void insertWrittenBy(String bookISBN, String authorSSN) throws SQLException;

    void insertBookByAuthor(Author author, Book book) throws SQLException;

    void insertBookTransaktion(Book book, String authorSSN) throws SQLException;

    void insertAuthorTransaktion(Author author, String bookISBN) throws SQLException;
}
