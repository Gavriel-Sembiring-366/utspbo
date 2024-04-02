package oop;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AdminTransLogController {
    
    @FXML public TableView<TransactionLog> tableViewItem;
    @FXML private TextField textFieldTransLogId;
    @FXML private TextField textFieldUserId;
    @FXML private TextField textFieldDatetime;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldItem;
    @FXML private TextField textFieldTotal;
    @FXML private Label labelTitle;
    @FXML private Label labelDate;
    @FXML private Label labelView;
    @FXML private Label labelTransLogId;
    @FXML private Label labelUserId;
    @FXML private Label labelDatetime;
    @FXML private Label labelSelectDate;
    @FXML private Label labelUsername;
    @FXML private Label labelItem;
    @FXML private Label labelTotal;
    @FXML private Label labelDescription;
    @FXML private Button buttonBatal;
    @FXML private Button buttonSwitch;
    @FXML private DatePicker datePickerDate;

    public void initialize(){
        
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelView,
            labelDescription,
            labelTransLogId,
            labelUserId,
            labelDatetime,
            labelUsername,
            labelItem,
            labelTotal,
            labelSelectDate
        );

        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldTransLogId,
            textFieldUserId,
            textFieldDatetime,
            textFieldUsername,
            textFieldItem,
            textFieldTotal
        );

        ObservableList<Button> buttons = FXCollections.observableArrayList(
            buttonBatal,
            buttonSwitch
        );

        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14.0));
            
        });
        buttons.forEach(button->{button.fontProperty().set(App.Montserrat(18.0));});
        textFields.forEach(textField ->{textField.fontProperty().set(App.Montserrat(12.0));});
        labelTitle.fontProperty().set(App.Montserrat(56.0));

        tableViewItem.getColumns().addAll(Item.getTransLogColumns("admin_trans_log"));
        DBConnector.initDBConnection();
        loadTransLogTable();
    }

    private void loadTableViewListener(){
        tableViewItem.setOnMouseClicked((MouseEvent event)->{
            int index = tableViewItem.getSelectionModel().getSelectedIndex();
            TransactionLog row = tableViewItem.getItems().get(index);

            String currentTime = (row.date).split(" ")[1];
            textFieldTransLogId.setText(Integer.toString(row.transactionID));
            textFieldUserId.setText(Integer.toString(row.userID));
            textFieldDatetime.setText(currentTime);
            textFieldUsername.setText(row.username);
            textFieldItem.setText(row.item);
            textFieldTotal.setText(Float.toString(row.total));
        });
    }

    private void loadTransLogTable(){
        try {
            TransactionLogDB.fetchAllTransactionLog().forEach(log->{
                tableViewItem.getItems().add(log);
            });
        }
        catch (Exception e)
        {
            System.out.println("Error :" + e);
        }
        loadTableViewListener();
    }

    private void reloadTable(String selectedDate){
        List<TransactionLog> transLogs = TransactionLogDB.getFilteredTransactionLogs(selectedDate);
        if (transLogs.isEmpty()){
            loadTransLogTable();
        }
        else{
            transLogs.forEach(log->{
                tableViewItem.getItems().add(log);
            });
            loadTableViewListener();
        }
    }

    @FXML
    private void actionExit() throws IOException{
        App.setRoot("login");
    }

    @FXML 
    private void actionFindDate() throws IOException, SQLException{
        tableViewItem.getItems().clear();
        LocalDate myDate = datePickerDate.getValue();
        reloadTable(myDate.toString());
    }

    @FXML
    private void actionSwitch() throws IOException {
        App.setRoot("adminItem");
    }
}
