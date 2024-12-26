package se.kth.olof.beyar.labb.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AppView
{
    BorderPane view;

    public AppView()
    {
        this.view = new BorderPane();
    }

    public void buildLayout(FlowPane navbar, VBox action) {
        view.setTop(navbar);
        view.setCenter(action);
    }

    public void rerenderActionLayout(VBox action)
    {
        view.setCenter(action);
    }

    public BorderPane getView()
    {
        return view;
    }
}
