package se.kth.olof.beyar.labb.model;

import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoSQLServiceProtocol implements DBServiceProtocol
{
    Connection connection;

    public NoSQLServiceProtocol(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public ArrayList<Book> findByAuthor(String query) throws SQLException
    {
        return null;
    }

    @Override
    public ArrayList<Book> findByISBN(String query) throws SQLException
    {
        return null;
    }

    @Override
    public ArrayList<Book> findByTitle(String query) throws SQLException
    {
        return null;
    }

    @Override
    public ArrayList<Book> findByGenre(String query) throws SQLException
    {
        return null;
    }

    @Override
    public ArrayList<Book> findByRating(String query) throws SQLException
    {
        return null;
    }

    @Override
    public void insertBook(Book book) throws SQLException
    {
    }

    @Override
    public void insertAuthor(Author author) throws SQLException
    {
    }

    @Override
    public void insertWrittenBy(String bookISBN, String authorSSN) throws SQLException
    {
    }

    @Override
    public void insertBookByAuthor(Author author, Book book) throws SQLException
    {
    }

    @Override
    public void insertBookTransaktion(Book book, String authorSSN) throws SQLException
    {
    }

    @Override
    public void insertAuthorTransaktion(Author author) throws SQLException
    {
    }
}
