module com.example.navalwarfare {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.navalwarfare to javafx.fxml;
    exports com.example.navalwarfare;
}