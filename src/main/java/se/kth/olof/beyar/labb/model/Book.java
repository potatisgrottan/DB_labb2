package se.kth.olof.beyar.labb.model;

import java.util.ArrayList;

/**
 * Represents a book with an ISBN,
 * title, genre, grade, and a list of authors.
 *  */
public class Book
{
    private String isbn;
    private final String title;
    private final ArrayList<String> listGenre;
    private final String grade;
    private final ArrayList<Author> authors;

    /**
     * Constructs a Book with the specified title, genre, ISBN, and grade.
     * @param title the title of the book
     * @param genre the genre of the book
     * @param isbn the ISBN of the book
     * @param grade the grade of the book
     * */
    public Book(String title, String genre, String isbn, String grade)
    {
        this.title = title;
        this.grade = grade;
        this.isbn = isbn;

        listGenre = new ArrayList<>();
        listGenre.add(genre);

        authors = new ArrayList<>();
    }

    /**
     * Adds an author to the list of authors who have written the book.
     * @param author the author to be added
     */
    public void addAuthor(Author author)
    {
        authors.add(author);
    }

    /**
     * Returns the title of the book.
     * @return the title of the book
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the grade of the book.
     * @return the grade of the book
     */
    public String getGrade()
    {
        return grade;
    }

    /**
     * Returns the ISBN of the book.
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /*
     * Joins multiple authors in array into one single string,
     * joined by a comma. This method is used for presentation purposes
     */
    public String getAuthorsJoined()
    {
        StringBuilder authorsJoined = new StringBuilder();
        for (Author author : authors)
        {
            authorsJoined
                    .append(author.getName())
                    .append(", ");
        }

        return authorsJoined.toString();
    }

     /**
     * Used to replace books with empty string as isbn to null
     * @param isbn the new ISBN of the book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /*
     * Transforms the array of genres into a string, delimited by a space
     * It walks through every element in the genre array and if there is a string
     * containing multiple genres in one string like a csv, then it splits it up further
     * and joins it into a string again. This is used for when presenting each book and it's genres
     * @return String of genres delimited by space
     */
    public String getGenres()
    {
        StringBuilder genreString = new StringBuilder();
        for (String genre : listGenre)
        {
            if (genre.contains(";"))
            {
                for (String subGenre : genre.split(";"))
                {
                    genreString.append(subGenre).append(", ");
                }
            }
            else
            {
                genreString.append(genre).append(" ");
            }
        }

        return genreString.toString();
    }

    /*
     * Serializes the array into a csv format
     * where every genre in the array is joined into one string
     * but separated by comma. This is used for storing the genres in database
     * @return String genre string of format csv
     */
    public String serializeGenres()
    {
        if (listGenre.size() == 1) return listGenre.getFirst();

        StringBuilder genreString = new StringBuilder();
        for (String genre : listGenre)
        {
            genreString.append(genre).append(";");
        }

        return genreString.toString();
    }

    @Override
    public String toString()
    {
        return "Book{" + "title: '" + title + ", genre: '" + listGenre + ", grade: " + grade + ", authorSSN:'" + authors + '}';
    }
}
