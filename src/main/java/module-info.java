module org.example.otp2_inclass2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.sql;


    opens org.example.otp2_inclass2 to javafx.fxml;
    exports org.example.otp2_inclass2;
}