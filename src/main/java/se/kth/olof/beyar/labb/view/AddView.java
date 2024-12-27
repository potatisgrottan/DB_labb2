package se.kth.olof.beyar.labb.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddView
{
    private HBox dialogOption;
    private VBox bookDialog;
    private Stage dialogPopup;
    private VBox authorDialog;
    private VBox connectAuthorToBookDialog;

    public AddView()
    {
    }

    public VBox createAddView()
    {
        Label addLabel = new Label("Add");
        Button addBoth = new Button("New book and author");
        Button addBook = new Button("New Book");
        Button addAuthor = new Button("New Author");
        Button connectAuthor = new Button("Connect Author to book");

        dialogOption = new HBox();
        dialogOption.getChildren().addAll(addBoth, addBook, addAuthor, connectAuthor);
        return new VBox(addLabel, dialogOption);
    }

    public Button getAddBothDialog()
    {
        return (Button) dialogOption.getChildren().getFirst();
    }

    public Button getAddBookDialog()
    {
        return (Button) dialogOption.getChildren().get(1);
    }

    public Button getAddAuthorDialog()
    {
        return (Button) dialogOption.getChildren().get(2);
    }

    public void createAndShowDialog(Stage stage, VBox addBox)
    {
        dialogPopup = new Stage();
        dialogPopup.initModality(Modality.WINDOW_MODAL);
        dialogPopup.initOwner(stage);
        Scene dialogScene = new Scene(addBox, 300, 280);
        dialogPopup.setScene(dialogScene);
        dialogPopup.show();
    }

    public VBox createAddBothBox()
    {
        HBox bothBox = new HBox();

        VBox bookBox = createAddBookBox();
        VBox authorBox = createAuthorBox();

        for(int i = 0; i<3; i++)
        {
            authorBox.getChildren().removeLast();
        }

        for(int i = 0; i<2;i++)
        {
            bookBox.getChildren().remove(9);
        }

        bothBox.getChildren().addAll(bookBox, authorBox);

        VBox bothVBox = new VBox();
        bothVBox.getChildren().addAll(bothBox);
        return bothVBox;
    }

    public VBox createAddBookBox()
    {
        Label addBookLabel = new Label("Add Book");
        Label isbnLabel = new Label("ISBN:");
        Label titleLabel = new Label("Title:");
        Label genreLabel = new Label("Genre:");
        Label gradeLabel = new Label("Grade:");
        Label authorLabel = new Label("Author SSN:");

        TextField titleBar = new TextField();
        titleBar.setPromptText("Title...");

        TextField isbnBar = new TextField();
        isbnBar.setPromptText("123-456789...");

        TextField gradeBar = new TextField();
        gradeBar.setPromptText("1 - 5");

        TextField genreBar = new TextField();
        genreBar.setPromptText("Sci-fi or Action;Thriller;Comedy");

        TextField authorBar = new TextField();
        authorBar.setPromptText("Existing author SSN...");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        HBox bookDialogAction = new HBox();
        bookDialogAction.getChildren().addAll(saveButton, cancelButton);

        bookDialog = new VBox();
        bookDialog.getChildren().addAll(addBookLabel, isbnLabel, isbnBar, titleLabel, titleBar,
                genreLabel, genreBar, gradeLabel, gradeBar, authorLabel, authorBar, bookDialogAction);

        return bookDialog;
    }

    public VBox createConnectAuthorToBookBox() {

        Label connectLabel = new Label("Connect a existing author to a book");
        Label isbnLabel = new Label("ISBN:");
        Label addBookLabel = new Label("SSN:");

        TextField isbnField = new TextField();
        isbnField.setPromptText("Write existing ISBN");

        TextField ssnField = new TextField();
        ssnField.setPromptText("Write existing SSN");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        HBox connectAuthorToBookHBoxDialog = new HBox();
        connectAuthorToBookHBoxDialog.getChildren().addAll(saveButton,cancelButton);

        connectAuthorToBookDialog = new VBox();
        connectAuthorToBookDialog.getChildren().addAll(connectLabel, isbnLabel, isbnField,
                addBookLabel, ssnField, connectAuthorToBookHBoxDialog);

        return connectAuthorToBookDialog;
    }

    public Stage getDialogPopup()
    {
        return dialogPopup;
    }

    public TextField getISBNInput()
    {
        return (TextField) bookDialog.getChildren().get(2);
    }

    public TextField getTitleInput()
    {
        return (TextField) bookDialog.getChildren().get(4);
    }

    public TextField getGenreInput()
    {
        return (TextField) bookDialog.getChildren().get(6);
    }

    public TextField getGradeInput()
    {
        return (TextField) bookDialog.getChildren().get(8);
    }

    public TextField getBookAuthorSSNInput()
    {
        return (TextField) bookDialog.getChildren().get(10);
    }

    public Button getBookDialogSaveButton()
    {
        return (Button) ((HBox) bookDialog.getChildren().getLast()).getChildren().getFirst();
    }

    public Button getBookDialogCancelButton()
    {
        return (Button) ((HBox) bookDialog.getChildren().getLast()).getChildren().get(1);
    }

    public VBox createAuthorBox()
    {
        Label addBookLabel = new Label("Add author");
        Label firstNameLabel = new Label("Full Name:");
        Label ssnLabel = new Label("Social Security Number:");
        Label bookISBNLabel = new Label("ISBN:");

        TextField firstNameBar = new TextField();
        firstNameBar.setPromptText("write the name here!");

        TextField ssnBar = new TextField();
        ssnBar.setPromptText("write social security number here!");

        TextField bookISBNBar = new TextField();
        bookISBNBar.setPromptText("write book isbn here!");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        HBox authorDialogAction = new HBox();
        authorDialogAction.getChildren().addAll(saveButton, cancelButton);

        authorDialog = new VBox();
        authorDialog.getChildren().addAll(
            addBookLabel,
            firstNameLabel, firstNameBar,
            ssnLabel, ssnBar,
            bookISBNLabel, bookISBNBar,
            authorDialogAction
        );

        return authorDialog;
    }

    public TextField getAuthorName()
    {
        return (TextField) authorDialog.getChildren().get(2);
    }

    public TextField getAuthorSSN()
    {
        return (TextField) authorDialog.getChildren().get(4);
    }

    public TextField getAuthorBookISBN()
    {
        return (TextField) authorDialog.getChildren().get(6);
    }

    public Button getAuthorDialogSaveButton()
    {
        return (Button) ((HBox) authorDialog.getChildren().getLast()).getChildren().getFirst();
    }

    public Button getAuthorDialogCancelButton()
    {
        return (Button) ((HBox) authorDialog.getChildren().getLast()).getChildren().get(1);
    }

    public Button getConnectAuthorToBookDialogSave(){
        return (Button) ((HBox) connectAuthorToBookDialog.getChildren().getLast()).getChildren().getFirst();
    }

    public Button getConnectAuthorToBookDialogCancel(){
        return (Button) ((HBox) connectAuthorToBookDialog.getChildren().getLast()).getChildren().getLast();
    }

    public TextField getConnectAuthorToBookISBN(){
        return (TextField) connectAuthorToBookDialog.getChildren().get(2);
    }

    public TextField getConnectAuthorToBookSSN(){
        return (TextField) connectAuthorToBookDialog.getChildren().get(4);
    }

    public Button getConnectAuthorToBookDialog()
    {
        return (Button) dialogOption.getChildren().get(3);
    }
}
