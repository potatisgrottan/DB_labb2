module se.kth.olof.beyar.labb {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    requires transitive javafx.graphics;
    requires transitive java.sql;

    opens se.kth.olof.beyar.labb to javafx.fxml;
    exports se.kth.olof.beyar.labb;
}
