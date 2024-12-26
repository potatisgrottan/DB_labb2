package se.kth.olof.beyar.labb.model;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoSQLServiceProtocol implements DBServiceProtocol
{
    MongoDatabase databaseConnection;

    public NoSQLServiceProtocol(MongoDatabase databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public ArrayList<Book> findByAuthor(String query) throws SQLException
    {
         /*
         {
        "name": ...,
        "SSN": ...,
        "Books": [
            {
                "Title": ...,
                "ISBN": ...,
                "Genre": ...,
                "Grade": ...,
            },
            ...
        ]
        },
        */
        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        Bson filter = Filters.regex("name", query, "i");
        FindIterable<Document> result = authors.find(filter);

        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Document author : result)
        {
            String name = author.getString("name");
            String ssn = author.getString("SSN");
            Author bookAuthor = new Author(name, ssn);

            List<Document> booksDocuments = (List<Document>) author.get("Books");
            for (Document bookDoc : booksDocuments) {
                Book book = new Book(
                        bookDoc.getString("Title"),
                        bookDoc.getString("Genre"),
                        bookDoc.getString("ISBN"),
                        bookDoc.getString("Grade")
                );
                book.addAuthor(bookAuthor);
                booksByAuthor.add(book);
            }
        }

        return booksByAuthor;
    }

    @Override
    public ArrayList<Book> findByISBN(String query) throws SQLException
    {
        return getBookByQuery(query, "ISBN");
    }

    @Override
    public ArrayList<Book> findByTitle(String query) throws SQLException
    {
        return getBookByQuery(query, "Title");
    }

    @Override
    public ArrayList<Book> findByGenre(String query)
    {
        return getBookByQuery(query, "Genres");
    }

    @Override
    public ArrayList<Book> findByRating(String query) throws SQLException
    {
        return getBookByQuery(query, "Grade");
    }

    @Override
    public void insertBook(Book book) throws SQLException
    {
        /*
        {
            "Title": book.getTitle(),
            "ISBN": book.getIsbn(),
            "Genres": book.getGenres(),
            "Grade": book.getGrade(),
            "Authors": book.getAuthors();
        },
        */
    }

    @Override
    public void insertAuthor(Author author) throws SQLException
    {
        Document authorInsert = new Document("name", author.getName());
        authorInsert.append("SSN", author.getSSN());
        authorInsert.append("books",author.getBooks());

        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        authors.insertOne(authorInsert);
    }

    //TODO gör till privat metod hos MySQLServiceProtocol
    @Override
    public void insertWrittenBy(String bookISBN, String authorSSN) throws SQLException
    {
        // insert
    }

    @Override
    public void insertBookByAuthor(Author author, Book book) throws SQLException
    {
        insertAuthor(author);
        insertBook(book);
    }

    @Override
    public void insertBookTransaktion(Book book, String author) throws SQLException
    {
        //Find author/s
        //if author/s exist{
        //create author
        // add author to written list in book
        //Insert book doc}
        //else cant do the insert

        ArrayList<Book> list = findByAuthor(author);
        book.addAuthor(list.getFirst().getAuthors().getFirst());
        insertBook(book);

        //try{findByAuthor(author))
        //newAuthor = get everything from author doc (name,ssn)
        //book.addAuthor(newAuthor);
        //insertBook(book);
        //}catch(Exception e){
        //throw new Exception(e);
        //}
    }

    @Override
    public void insertAuthorTransaktion(Author author,String bookISBN) throws SQLException
    {
        //Find book/s
        //if book/s exist{
        //create book/s
        // add book/s to written list in author
        //Insert author doc }
        //else cant do the insert

        ArrayList<Book> list = findByISBN(bookISBN);
        author.addBook(list.getFirst());
        insertAuthor(author);

        //try{findByISBN(bookISBN).size() == 1)
        //newBook = get everything from book doc (title,genre,grade,isbn)
        //author.addBook(newBook);
        //insertAuthor(author);
        //}catch(Exception e){
        //throw new Exception(e);
        //}
    }

    private ArrayList<Book> getBookByQuery(String query, String category)
    {
        /*{
            "Title": ...,
            "ISBN": ...,
            "Genres": [...],
            "Grade": ...,
            "Authors": [
            {
                "name": ...,
                "SSN": ...
            },
            ...
            ]
        },*/

        MongoCollection<Document> books = databaseConnection.getCollection("Books");
        Bson filter = Filters.regex(category, query, "i");
        FindIterable<Document> result = books.find(filter);

        ArrayList<Book> booksResult = new ArrayList<>();
        for (Document book : result) {
            String title = book.getString("Title");
            String isbn = book.getString("ISBN");
            List<String> genres = book.getList("Genres", String.class);
            String grade = book.getString("Grade");

            Book newBook = new Book(
                    title,
                    genres.toString(),
                    isbn,
                    grade
            );

            List<Document> authorsArray = book.getList("Authors", Document.class);
            for (Document author : authorsArray) {
                String authorName = author.getString("name");
                String authorSSN = author.getString("SSN");

                Author newAuthor = new Author(authorName, authorSSN);
                newBook.addAuthor(newAuthor);
            }

            booksResult.add(newBook);
        }

        return booksResult;
    }
}
