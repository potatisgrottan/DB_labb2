package se.kth.olof.beyar.labb.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.kth.olof.beyar.labb.common.SearchOptions;

public class SearchView
{
    private HBox searchApp;
    private TextArea searchResultsArea;
    private ComboBox<SearchOptions> searchOptions;

    public SearchView()
    {
    }

    public VBox createSearchView()
    {
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for books or authors here!");

        Label searchForLabel = new Label("Search for");
        ObservableList<SearchOptions> optionsValues = FXCollections.observableArrayList(SearchOptions.values());
        searchOptions = new ComboBox<>(optionsValues);
        searchOptions.setPromptText("Title");
        searchOptions.setValue(SearchOptions.Title);

        Button searchButton = new Button("Search");
        Label searchLabel = new Label("Search");

        searchResultsArea = new TextArea();
        searchResultsArea.setEditable(false);
        searchResultsArea.setWrapText(true);

        searchApp = new HBox();
        searchApp.getChildren().addAll(searchBar, searchButton);

        VBox verticalSearchBox = new VBox();
        verticalSearchBox.getChildren().addAll(
                searchLabel, searchApp,
                searchForLabel, searchOptions,
                searchResultsArea
        );

        return verticalSearchBox;
    }

    public ComboBox<SearchOptions> getSearchOptions(){
        return searchOptions;
    }

    public Button getSearchButton()
    {
        return (Button) searchApp.getChildren().get(1);
    }

    public TextField getSearchBar()
    {
        return (TextField) searchApp.getChildren().getFirst();
    }

    public void setResponseText(String text)
    {
        searchResultsArea.setText(text);
    }
}
