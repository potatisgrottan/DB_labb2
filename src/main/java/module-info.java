module se.kth.olof.beyar.dbl1.db_labb2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.olof.beyar.dbl1.db_labb2 to javafx.fxml;
    exports se.kth.olof.beyar.dbl1.db_labb2;
}