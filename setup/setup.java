package se.kth.olof.beyar.labb.setup;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class DatabaseSetup {
    public static void main(String[] args) {
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

        try (MongoClient client = MongoClients.create(settings)) {
            MongoDatabase database = client.getDatabase("Library");

            // Drop existing collections if they exist
            try {
                database.getCollection("Books").drop();
                database.getCollection("Authors").drop();
            } catch (Exception e) {
                System.out.println("No existing collections to drop");
            }

            // Create Books collection with a sample book
            database.createCollection("Books");
            Document sampleBook = new Document()
                    .append("Title", "Sample Book")
                    .append("ISBN", "978-1234567890")
                    .append("Genres", Arrays.asList("Action", "Adventure"))
                    .append("Grade", "A")
                    .append("Authors", Arrays.asList(
                            new Document()
                                    .append("name", "Stefan kvist")
                                    .append("SSN", "19700101-1234")
                    ));
            database.getCollection("Books").insertOne(sampleBook);

            // Skapa Authors collection
            database.createCollection("Authors");
            Document sampleAuthor = new Document()
                    .append("name", "Stefan Kvist")
                    .append("SSN", "19700101-1234")
                    .append("Books", Arrays.asList(
                            new Document()
                                    .append("Title", "Sample Bok")
                                    .append("ISBN", "978-1234567890")
                                    .append("Genres", "Action;Adventure")
                                    .append("Grade", "3")
                    ));
            database.getCollection("Authors").insertOne(sampleAuthor);

            System.out.println("Database setup completed successfully!");
            System.out.println("Created database: Library");
            System.out.println("Created collections: Books and Authors");

        } catch (Exception e) {
            System.err.println("Error setting up database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}