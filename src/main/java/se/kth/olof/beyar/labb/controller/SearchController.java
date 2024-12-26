package se.kth.olof.beyar.labb.controller;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import se.kth.olof.beyar.labb.common.BooksDBException;
import se.kth.olof.beyar.labb.common.SearchOptions;
import se.kth.olof.beyar.labb.model.Book;
import se.kth.olof.beyar.labb.model.SearchModel;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;
import se.kth.olof.beyar.labb.view.SearchView;

import java.sql.SQLException;
import java.util.ArrayList;

public class SearchController
{
    SearchModel model;
    SearchView view;
    DBServiceProtocol databaseService;

    public SearchController(SearchView view, SearchModel model, DBServiceProtocol databaseService)
    {
        this.model = model;
        this.view = view;
        this.databaseService = databaseService;
    }

    public void addEventListener()
    {
        view.getSearchButton().setOnAction(_ ->
        {
            String query = view.getSearchBar().getText();
            queryDBByText(query);
        });
    }

    public void queryDBByText(String find)
    {
        new Thread(() -> {
            ArrayList<Book> books;
            try
            {
                if (view.getSearchOptions().getValue() == SearchOptions.ISBN)
                {
                    books = databaseService.findByISBN(find);
                } else if (view.getSearchOptions().getValue() == SearchOptions.Title)
                {
                    books = databaseService.findByTitle(find);
                } else if (view.getSearchOptions().getValue() == SearchOptions.Genre)
                {
                    books = databaseService.findByGenre(find);
                } else if (view.getSearchOptions().getValue() == SearchOptions.Rating)
                {
                    books = databaseService.findByRating(find);
                } else
                {
                    books = databaseService.findByAuthor(find);
                }
            } catch (SQLException e)
            {
                throw new BooksDBException(e);
            }

            Platform.runLater(() -> setResult(books));
        }).start();
    }

    public void setResult(ArrayList<Book> books)
    {
        StringBuilder response = new StringBuilder();
        books.forEach(book -> {
            // System.out.println(book);
            response
                    .append("ISBN: ").append(book.getIsbn()).append("\n")
                    .append("Title: ").append(book.getTitle()).append("\n")
                    .append("Genre: ").append(book.getGenres()).append("\n")
                    .append("Rating: ").append(book.getGrade()).append("\n")
                    .append("Author: ").append(book.getAuthorsJoined()).append("\n\n");
        });
        view.setResponseText(response.toString());
    }

    public VBox createSearchView()
    {
        // The listener has to be attached before returning the view (and therefore creating it)
        // this is because otherwise, the event listener won't react when pressing the button
        VBox createdSearchView = view.createSearchView();
        addEventListener();
        return createdSearchView;
    }
}
