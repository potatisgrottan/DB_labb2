package se.kth.olof.beyar.labb.model;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of the NoSQL service protocol for MongoDB.
 */
public class NoSQLServiceProtocol implements DBServiceProtocol
{
    MongoDatabase databaseConnection;

    /**
     * Constructs a NoSQLServiceProtocol with the specified database connection.
     *
     * @param databaseConnection the MongoDB database connection
     */
    public NoSQLServiceProtocol(MongoDatabase databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Finds books by author name.
     *
     * @param query the author name query
     * @return a list of books by the specified author
     */
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

            @SuppressWarnings("unchecked")
            List<Document> booksDocuments = (List<Document>) author.get("Books");
            for (Document bookDocument : booksDocuments) {
                Book book = new Book(
                        bookDocument.getString("Title"),
                        bookDocument.get("Genres").toString(),
                        bookDocument.getString("ISBN"),
                        bookDocument.getString("Grade")
                );
                book.addAuthor(bookAuthor);
                booksByAuthor.add(book);
            }
        }

        return booksByAuthor;
    }

    /**
     * Finds books by ISBN.
     *
     * @param query the ISBN query
     * @return a list of books with the specified ISBN
     */
    @Override
    public ArrayList<Book> findByISBN(String query)
    {
        return getBookByQuery(query, "ISBN");
    }

    /**
     * Finds books by title.
     *
     * @param query the title query
     * @return a list of books with the specified title
     */

    @Override
    public ArrayList<Book> findByTitle(String query)
    {
        return getBookByQuery(query, "Title");
    }

    /**
     * Finds books by genre.
     *
     * @param query the genre query
     * @return a list of books with the specified genre
     */

    @Override
    public ArrayList<Book> findByGenre(String query)
    {
        return getBookByQuery(query, "Genres");
    }

    /**
     * Finds books by rating.
     *
     * @param query the rating query
     * @return a list of books with the specified rating
     */

    @Override
    public ArrayList<Book> findByRating(String query)
    {
        return getBookByQuery(query, "Grade");
    }

    /**
     * Inserts a new book document into the database.
     *
     * @param book the book to insert
     */
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

    /**
     * Inserts a new author document into the database.
     *
     * @param author the author to insert
     */
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
                        .append("Genres", book.getGenres())
                        .append("Grade", book.getGrade())
            );
        }

        authorInsert.append("Books", booksDocuments);

        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        authors.insertOne(authorInsert);
    }

    /**
     * Links a book to an author by updating their respective documents.
     *
     * @param bookISBN the ISBN of the book
     * @param authorSSN the SSN of the author
     * @throws IllegalStateException if the author is not found
     */
    @Override
    public void insertWrittenBy(String bookISBN, String authorSSN) throws IllegalStateException
    {
        Book book = findByISBN(bookISBN).getFirst();

        MongoCollection<Document> authors = databaseConnection.getCollection("Authors");
        Document author = authors.find(eq("SSN", authorSSN)).first();

        Author newAuthor;
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

    /**
     * Inserts an author and a book into the database.
     *
     * @param author the author to insert
     * @param book the book to insert
     */
    @Override
    public void insertBookByAuthor(Author author, Book book)
    {
        insertAuthor(author);
        insertBook(book);
    }


    /**
     * Inserts a book and updates an author's document.
     *
     * @param book the book to insert
     * @param authorSSN the SSN of the author to update
     */
    @Override
    public void insertBookUpdateAuthor(Book book, String authorSSN)
    {
        MongoCollection<Document> booksCollection = databaseConnection.getCollection("Books");
        MongoCollection<Document> authorsCollection = databaseConnection.getCollection("Authors");

        List<Document> authorsList = new ArrayList<>();
        Document author = authorsCollection.find(eq("SSN", authorSSN)).first();

        if (author == null)
            throw new IllegalStateException("No author found");

        authorsList.add(
                new Document()
                        .append("name", author.getString("name"))
                        .append("SSN", authorSSN)
        );

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
                .append("Genres", book.getGenres())
                .append("Grade", book.getGrade());
        authorsCollection.updateOne(
                eq("SSN", authorSSN),
                new Document("$push", new Document("Books", newBookForAuthor))
        );
    }

    /**
     * Inserts a new author and links them to an existing book.
     *
     * @param author The Author object to insert
     * @param bookISBN The ISBN of the existing book to link
     * @throws RuntimeException if the specified book is not found in the database
     */
    @Override
    public void insertAuthorUpdateBook(Author author, String bookISBN)
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
                .append("Genres", bookDocument.get("Genres"))
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

    /**
     * Helper method to perform generic book queries based on different criteria.
     *
     * @param query The search query string
     * @param category The category to search in (e.g., "ISBN", "Title", "Genres", "Grade")
     * @return ArrayList of Books matching the search criteria
     */
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
            if (authorsArray == null)
                System.out.println("[ERROR] Book: " + newBook + " has no authors");

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
