package se.kth.olof.beyar.labb.controller;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import se.kth.olof.beyar.labb.common.Views;
import se.kth.olof.beyar.labb.model.NavbarModel;
import se.kth.olof.beyar.labb.view.NavbarView;

import java.util.function.Consumer;

public class NavbarController
{
    NavbarModel model;
    NavbarView view;
    private Consumer<Views> viewHandler;

    public NavbarController(NavbarModel model, NavbarView view)
    {
        this.model = model;
        this.view = view;
    }

    public void setViewHandler(Consumer<Views> updateViewCallback) {
        this.viewHandler = updateViewCallback;
    }

    private void sendCallbackValue()
    {
        if (viewHandler != null) {
            viewHandler.accept(model.getChosenView());
        }
    }

    public void initializeListeners()
    {
        view.getSearchButton().setOnAction(_ -> {
            model.setChosenView(Views.SEARCH);
            sendCallbackValue();
        });

        view.getAddButton().setOnAction(_ -> {
            model.setChosenView(Views.ADD);
            sendCallbackValue();
        });
    }

    public FlowPane getNavbar()
    {
        return view.getNavbar();
    }

    public void focusButtonOnStart(Button button)
    {
        button.requestFocus();
    }
}
