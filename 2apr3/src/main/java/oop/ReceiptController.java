package oop;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ReceiptController {
    @FXML private TableView<Purchase> tableViewItem;
    @FXML private Text textTitle;
    @FXML private Label labelNama;
    @FXML private Label labelTanggal;
    @FXML private Label labelBayar;
    @FXML private Label labelKembalian;
    @FXML private TextField textFieldNama;
    @FXML private TextField textFieldTanggal;
    @FXML private TextField textFieldBayar;
    @FXML private TextField textFieldKembalian;

    UserIdentity currentUser;

    public void initialize(){
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelNama,
            labelTanggal,
            labelBayar,
            labelKembalian
        );
        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldNama,
            textFieldTanggal,
            textFieldBayar,
            textFieldKembalian
        );
        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14.0));});
        textFields.forEach(textField ->{textField.fontProperty().set(App.Montserrat(12.0));});
        textTitle.fontProperty().set(App.Montserrat(56));

        tableViewItem.getColumns().addAll(Item.getItemColumns("receipt"));
    }
    
    public void setCurrentUser(UserIdentity ui){
        currentUser = ui;
    }
    public void loadTable(){
        currentUser.getItemList().forEach(item->{tableViewItem.getItems().add(item);});
    }
    public void loadInformation(float pay, float change){
        textFieldNama.setText(currentUser.getUserName());
        textFieldTanggal.setText(LocalDateTime.now().getDayOfWeek() +", "+ App.getCurrentDateTime());
        textFieldBayar.setText(Float.toString(pay));
        textFieldKembalian.setText(Float.toString(change));
    }
}
