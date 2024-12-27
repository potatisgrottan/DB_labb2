package se.kth.olof.beyar.labb.model;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class NoSQLServiceProtocol implements DBServiceProtocol
{
    MongoDatabase databaseConnection;
    private final MongoClient client;

    public NoSQLServiceProtocol(MongoDatabase databaseConnection, MongoClient client)
    {
        this.databaseConnection = databaseConnection;
        this.client = client;
    }

    @Override
    public ArrayList<Book> findByAuthor(String query)
    {
        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        Bson filter = Filters.regex("name", query, "i");
        FindIterable<Document> result = authors.find(filter);

        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Document author : result) {
            String name = author.getString("name");
            String ssn = author.getString("SSN");
            Author bookAuthor = new Author(name, ssn);

            List<Document> booksDocuments = (List<Document>) author.get("Books");
            if (booksDocuments != null) {
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
        }

        return booksByAuthor;
    }

    @Override
    public ArrayList<Book> findByISBN(String query)
    {
        return getBookByQuery(query, "ISBN");
    }

    @Override
    public ArrayList<Book> findByTitle(String query)
    {
        return getBookByQuery(query, "Title");
    }

    @Override
    public ArrayList<Book> findByGenre(String query)
    {
        return getBookByQuery(query, "Genres");
    }

    @Override
    public ArrayList<Book> findByRating(String query)
    {
        return getBookByQuery(query, "Grade");
    }

    @Override
    public void insertBook(Book book)
    {
        Document bookDocument = new Document();
        bookDocument
                .append("Title", book.getTitle())
                .append("ISBN", book.getIsbn())
                .append("Genres", Arrays.asList(book.getGenres().split("\\s*,\\s*")))
                .append("Grade", book.getGrade());

        List<Document> authorDocs = new ArrayList<>();
        for (Author author : book.getAuthors())
        {
            Document authorDoc = new Document()
                    .append("name", author.getName())
                    .append("SSN", author.getSSN());

            authorDocs.add(authorDoc);
        }
        bookDocument.append("Authors", authorDocs);

        MongoCollection<Document> books = databaseConnection.getCollection("Books");
        books.insertOne(bookDocument);
    }

    @Override
    public void insertAuthor(Author author)
    {
        Document authorInsert = new Document("name", author.getName());
        authorInsert.append("SSN", author.getSSN());

        List<Document> booksDocuments = new ArrayList<>();
        for (Book book : author.getBooks()) {
            booksDocuments.add(
                new Document("Title", book.getTitle())
                        .append("ISBN", book.getIsbn())
                        .append("Genre", book.getGenres())
                        .append("Grade", book.getGrade())
            );
        }

        authorInsert.append("Books", booksDocuments);

        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        authors.insertOne(authorInsert);
    }

    @Override
    public void insertWrittenBy(String bookISBN, String authorSSN) throws IllegalStateException
    {
        Book book = findByISBN(bookISBN).getFirst();

        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        Document author = authors.find(eq("SSN", authorSSN)).first();

        Author newAuthor = null;
        if (author != null)
        {
            newAuthor = new Author(author.getString("name"), authorSSN);
        } else
        {
            throw new IllegalStateException("No author found");
        }

        Document bookDocument = new Document()
                .append("Title", book.getTitle())
                .append("ISBN", book.getIsbn())
                .append("Genres", Arrays.asList(book.getGenres().split("\\s*,\\s*")))
                .append("Grade", book.getGrade());

        Document authorDocument = new Document()
                .append("name", newAuthor.getName())
                .append("SSN", newAuthor.getSSN());

        Document addBookToAuthorUpdate = new Document("$push", new Document("Books", bookDocument));
        Document addAuthorToBookUpdate = new Document("$push", new Document("Authors", authorDocument));

        Document bookFilter = new Document("ISBN", bookISBN);
        MongoCollection<Document> books = databaseConnection.getCollection("Books");
        books.updateOne(bookFilter, addAuthorToBookUpdate);

        Document authorFilter = new Document("SSN", authorSSN);
        authors.updateOne(authorFilter, addBookToAuthorUpdate);
    }

    @Override
    public void insertBookByAuthor(Author author, Book book)
    {
        insertAuthor(author);
        insertBook(book);
    }

    @Override
    public void insertBookUpdateAuthor(Book book, String authorSSN)
    {
        MongoCollection<Document> booksCollection = databaseConnection.getCollection("Books");
        MongoCollection<Document> authorsCollection = databaseConnection.getCollection("Authors");

        List<Document> authorsList = new ArrayList<>();
        for (Author author : book.getAuthors())
        {
            authorsList.add(
                    new Document()
                            .append("name", author.getName())
                            .append("SSN", author.getSSN())
            );
        }

        Document newBook = new Document()
                .append("Title", book.getTitle())
                .append("ISBN", book.getIsbn())
                .append("Genres", Arrays.asList(book.getGenres().split(",")))
                .append("Grade", book.getGrade())
                .append("Authors", authorsList);

        booksCollection.insertOne(newBook);

        Document newBookForAuthor = new Document()
                .append("Title", book.getTitle())
                .append("ISBN", book.getIsbn())
                .append("Genre", book.getGenres())
                .append("Grade", book.getGrade());

        authorsCollection.updateOne(
                eq("SSN", authorSSN),
                new Document("$push", new Document("Books", newBookForAuthor))
        );
    }

    @Override
    public void insertAuthorUpdateBook(Author author, String bookISBN) throws SQLException
    {
        MongoCollection<Document> authorsCollection = databaseConnection.getCollection("Authors");
        MongoCollection<Document> booksCollection = databaseConnection.getCollection("Books");

        Document newAuthor = new Document()
                .append("name", author.getName())
                .append("SSN", author.getSSN())
                .append("Books", new ArrayList<>());

        authorsCollection.insertOne(newAuthor);

        Document bookDocument = booksCollection.find(eq("ISBN", bookISBN)).first();
        if (bookDocument == null)
        {
            throw new RuntimeException("Book with ISBN " + bookISBN + " not found.");
        }

        Document newBookForAuthor = new Document()
                .append("Title", bookDocument.getString("Title"))
                .append("ISBN", bookDocument.getString("ISBN"))
                .append("Genre", bookDocument.get("Genres"))
                .append("Grade", bookDocument.getString("Grade"));

        authorsCollection.updateOne(
                eq("SSN", author.getSSN()),
                new Document("$push", new Document("Books", newBookForAuthor))
        );

        Document newAuthorForBook = new Document()
                .append("name", author.getName())
                .append("SSN", author.getSSN());

        booksCollection.updateOne(
                eq("ISBN", bookISBN),
                new Document("$push", new Document("Authors", newAuthorForBook))
        );
    }

    private ArrayList<Book> getBookByQuery(String query, String category)
    {
        MongoCollection<Document> books = databaseConnection.getCollection("Books");
        Bson filter = Filters.regex(category, query, "i");
        FindIterable<Document> result = books.find(filter);

        ArrayList<Book> booksResult = new ArrayList<>();
        for (Document book : result)
        {
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
            for (Document author : authorsArray)
            {
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
