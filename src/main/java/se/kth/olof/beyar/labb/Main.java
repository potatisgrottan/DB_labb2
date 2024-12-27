package se.kth.olof.beyar.labb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import se.kth.olof.beyar.labb.controller.*;
import se.kth.olof.beyar.labb.model.*;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;
import se.kth.olof.beyar.labb.view.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException
    {
        String host = "localhost";
        String port = "27017";
        String uri = "mongodb://" + host + ":" + port;

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        MongoClient client = MongoClients.create(settings);
        MongoDatabase database = client.getDatabase("Library");

        DBServiceProtocol databaseService = new NoSQLServiceProtocol(database);

        stage.setOnCloseRequest(_ -> client.close());

        NavbarModel navbarModel = new NavbarModel();
        NavbarView navbarView = new NavbarView();
        NavbarController navbarController = new NavbarController(navbarModel, navbarView);
        navbarController.initializeListeners();

        SearchModel searchModel = new SearchModel();
        SearchView searchView = new SearchView();
        SearchController searchController = new SearchController(searchView, searchModel, databaseService);

        AddView addView = new AddView();
        AddController addController = new AddController(addView, databaseService);

        AppView appView = new AppView();
        AppController appController = new AppController(stage, appView, navbarController, searchController, addController);
        appController.buildLayout(
                navbarController.getNavbar(),
                searchController.createSearchView()
        );

        Scene scene = new Scene(appController.getView(), 500, 250);
        stage.setTitle("Library application");
        stage.setScene(scene);
        stage.show();
    }
}
