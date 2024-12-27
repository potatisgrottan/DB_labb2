package se.kth.olof.beyar.labb.controller;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.kth.olof.beyar.labb.common.BooksDBException;
import se.kth.olof.beyar.labb.model.Author;
import se.kth.olof.beyar.labb.model.Book;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;
import se.kth.olof.beyar.labb.view.AddView;

import java.sql.SQLException;

public class AddController
{
    AddView view;
    DBServiceProtocol databaseService;

    public AddController(AddView view, DBServiceProtocol databaseService)
    {
        this.view = view;
        this.databaseService = databaseService;
    }

    public void initializeListeners(Stage stage)
    {
        view.getAddBothDialog().setOnAction(_ -> {
            view.createAndShowDialog(stage, view.createAddBothBox());

            view.getBookDialogSaveButton().setOnAction(_ -> {
                new Thread(() -> {
                    readValuesFromBothDialog();
                    Platform.runLater(() -> {
                        view.getDialogPopup().close();
                    });
                }).start();
            });

            view.getBookDialogCancelButton().setOnAction(_ -> view.getDialogPopup().close());
        });

        view.getAddBookDialog().setOnAction(_ -> {
            view.createAndShowDialog(stage, view.createAddBookBox());

            view.getBookDialogSaveButton().setOnAction(_ -> {
                new Thread(() -> {
                    readValuesFromBookDialog();
                    Platform.runLater(() -> {
                        view.getDialogPopup().close();
                    });
                }).start();
            });

            view.getBookDialogCancelButton().setOnAction(_ -> view.getDialogPopup().close());
        });

        view.getAddAuthorDialog().setOnAction(_ -> {
            view.createAndShowDialog(stage, view.createAuthorBox());

            view.getAuthorDialogSaveButton().setOnAction(_ -> {
                new Thread(() -> {
                    readValuesFromAuthorDialog();
                    Platform.runLater(() -> {
                        view.getDialogPopup().close();
                    });
                }).start();
            });

            view.getAuthorDialogCancelButton().setOnAction(_ -> view.getDialogPopup().close());
        });

        view.getConnectAuthorToBookDialog().setOnAction(_ -> {
            view.createAndShowDialog(stage, view.createConnectAuthorToBookBox());

            view.getConnectAuthorToBookDialogSave().setOnAction(_ -> {
                new Thread(() -> {
                        readValuesFromConnectAuthorToBookDialog();
                    Platform.runLater(() -> {
                        view.getDialogPopup().close();
                    });
                }).start();
            });

            view.getConnectAuthorToBookDialogCancel().setOnAction(_ -> view.getDialogPopup().close());
        });
    }

    public void readValuesFromBookDialog()
    {
        String title = view.getTitleInput().getText();
        String genre = view.getGenreInput().getText();
        String isbn = view.getISBNInput().getText();
        String grade = view.getGradeInput().getText();
        String authorSSN = view.getBookAuthorSSNInput().getText();

        Book book = new Book(title, genre, isbn, grade);

        // We want to substitute the empty string value with null so the DBs constraints can detect it
        if (book.getIsbn().isEmpty())
        {
            book.setIsbn(null);
        }

        try
        {
            databaseService.insertBookUpdateAuthor(book, authorSSN);
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }

        System.out.println("[DEBUG]" + isbn + ", " + title + ", " + genre + ", " + grade + ", " + authorSSN);
    }

    public void readValuesFromAuthorDialog() throws BooksDBException
    {
        Author author;
        String name = view.getAuthorName().getText();
        String ssn = view.getAuthorSSN().getText();
        String bookISBN = view.getAuthorBookISBN().getText();

        author = new Author(name, ssn);
        //search for book by isbn
        // omvandla till book

       // author.addBook(bookISBN);// add created book
        System.out.println(name + ", " + ssn + ", " + bookISBN);

        if (author.getSSN().isEmpty())
        {
            author.setSsn(null);
        }

        try
        {
            databaseService.insertAuthorUpdateBook(author, bookISBN);
        } catch (SQLException e)
        {
            throw new BooksDBException(e);
        }
    }

    private void readValuesFromBothDialog() throws BooksDBException
    {
        String name = view.getAuthorName().getText();
        String ssn = view.getAuthorSSN().getText();

        String isbn = view.getISBNInput().getText();
        String title = view.getTitleInput().getText();
        String genre = view.getGenreInput().getText();
        String grade = view.getGradeInput().getText();

        Author a = new Author(name, ssn);
        Book b = new Book(title, genre, isbn, grade);
        b.addAuthor(a);
        a.addBook(b);

        if (b.getIsbn().isEmpty())
        {
            b.setIsbn(null);
        }

        if (a.getSSN().isEmpty())
        {
            a.setSsn(null);
        }

        try
        {
            databaseService.insertBookByAuthor(a, b);
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }
    }

    private void readValuesFromConnectAuthorToBookDialog() throws BooksDBException
    {
        String isbn = view.getConnectAuthorToBookISBN().getText();
        String ssn = view.getConnectAuthorToBookSSN().getText();

        try
        {
            databaseService.insertWrittenBy(isbn, ssn);
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }

        System.out.println(isbn + ", " + ssn);
    }

    public VBox createAddView()
    {
        return view.createAddView();
    }
}
