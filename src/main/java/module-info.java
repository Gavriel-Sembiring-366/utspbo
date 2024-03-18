module oop {
    requires javafx.controls;
    requires javafx.fxml;

    opens oop to javafx.fxml;
    exports oop;
}
