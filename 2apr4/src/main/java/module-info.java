module oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens oop to javafx.fxml;
    exports oop;
}
