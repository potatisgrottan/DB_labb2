package se.kth.olof.beyar.labb;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import se.kth.olof.beyar.labb.common.BooksDBException;
import se.kth.olof.beyar.labb.controller.*;
import se.kth.olof.beyar.labb.model.*;
import se.kth.olof.beyar.labb.protocol.DBServiceProtocol;
import se.kth.olof.beyar.labb.view.*;
import java.io.IOException;
import java.sql.*;

import static com.mongodb.client.model.Filters.eq;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException
    {
        String uri = "mongodb://localhost:27017/Library";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("admin");

        // Create a new client and connect to the server
        try {
            // Send a ping to confirm a successful connection
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException me) {
            System.err.println(me);
        }

        MongoCollection<Document> collection = database.getCollection("admin");
        collection.find().first();

        // Construct a ServerApi instance using the ServerApi.builder() method
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        // databaseService = new MySQLServiceProtocol(connection);

        Database db = new Database("Library_v2");
        DBServiceProtocol databaseService;
        try
        {
            Connection connection = db.connect();
            databaseService = new NoSQLServiceProtocol(connection);
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        stage.setOnCloseRequest(_ -> {
            try
            {
                db.disconnect();
            }
            catch (SQLException e)
            {
                throw new BooksDBException(e);
            }
        });

        NavbarModel navbarModel = new NavbarModel();
        NavbarView navbarView = new NavbarView();
        NavbarController navbarController = new NavbarController(navbarModel, navbarView);
        navbarController.initializeListeners();

        SearchModel searchModel = new SearchModel();
        SearchView searchView = new SearchView();
        SearchController searchController = new SearchController(searchView, searchModel, databaseService);

        AddView addView = new AddView();
        AddController addController = new AddController(addView,databaseService);

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
