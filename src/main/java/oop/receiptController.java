package oop;


import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;

public class receiptController {

    @FXML private TableView<Purchase> tableViewItem;
    
    @FXML
    public void initialize() {
        ObservableList<Purchase> data = tableViewItem.getItems();
        
    }

    public static void main(String args[]){
        
    }
}
