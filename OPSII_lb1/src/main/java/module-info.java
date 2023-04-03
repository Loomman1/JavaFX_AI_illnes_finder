module com.example.opsii_lb1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.opsii_lb1 to javafx.fxml;
    exports com.example.opsii_lb1;
}