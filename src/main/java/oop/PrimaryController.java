package oop;

//import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class PrimaryController {
    @FXML
    private TextField item_quantity;

    @FXML
    private CheckBox search_switch;

    @FXML
    private Label search_switch_label;

    @FXML
    private ComboBox<String> item_combobox;

    public void initialize() {
        // Initialize the items
        ObservableList<String> items = FXCollections.observableArrayList(
                "Apple", "Banana", "Cherry", "Grape", "Lemon", "Orange", "Strawberry"
        );
        item_combobox.setItems(items);

        item_combobox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue.toLowerCase();
            ObservableList<String> filteredItems = items.filtered(item -> item.toLowerCase().startsWith(filterText));
            item_combobox.setItems(filteredItems);

        });
        
    }   


    @FXML
    private void tambah(ActionEvent e) {
        String jumlah = item_quantity.getText();
        System.out.println(jumlah);
    }


}
