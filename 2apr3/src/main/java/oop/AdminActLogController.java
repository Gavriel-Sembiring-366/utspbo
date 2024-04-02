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


public class AdminActLogController {
    
    @FXML public TableView<ActivityLog> tableViewItem;
    @FXML private TextField textFieldLogId;
    @FXML private TextField textFieldUserId;
    @FXML private TextField textFieldDatetime;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldType;
    @FXML private Label labelTitle;
    @FXML private Label labelDate;
    @FXML private Label labelView;
    @FXML private Label labelLogId;
    @FXML private Label labelUserId;
    @FXML private Label labelDatetime;
    @FXML private Label labelUsername;
    @FXML private Label labelType;
    @FXML private Label labelDescription;
    @FXML private Button buttonBatal;
    @FXML private Button buttonSwitch;
    @FXML private DatePicker datePickerDate;

    public void initialize(){
        
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelDate, 
            labelView,
            labelDescription,
            labelLogId,
            labelUserId,
            labelDatetime,
            labelUsername,
            labelType
        );

        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldLogId,
            textFieldUserId,
            textFieldDatetime,
            textFieldUsername,
            textFieldType
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

        tableViewItem.getColumns().addAll(Item.getLogColumns("admin_log"));
        DBConnector.initDBConnection();
        loadLogTable();
    }

    private void loadTableViewListener(){
        tableViewItem.setOnMouseClicked((MouseEvent event)->{
            int index = tableViewItem.getSelectionModel().getSelectedIndex();
            ActivityLog row = tableViewItem.getItems().get(index);

            String currentTime = (row.date).split(" ")[1];
            textFieldLogId.setText(Integer.toString(row.idAct));
            textFieldUserId.setText(Integer.toString(row.idUser));
            textFieldDatetime.setText(currentTime);
            textFieldUsername.setText(row.username);
            textFieldType.setText(row.typeAct);

            labelDescription.setText(
                row.username + " melakukan " +
                ActivityLogDB.convertTypeActToDesc(row.typeAct) + " pada waktu " + 
                currentTime
            );
        });
    }

    private void loadLogTable(){
        try {
            ActivityLogDB.fetchAllActivityLog().forEach(log->{
                tableViewItem.getItems().add(log);
            });;
        }
        catch (Exception e)
        {
            System.out.println("Error :" + e);
        }   
        loadTableViewListener();
    }

    private void reloadTable(String date){
        List<ActivityLog> actLog = ActivityLogDB.getFilteredActivityLogs(date);
        if(actLog.isEmpty()){
            loadLogTable();
        }
        else {
            actLog.forEach(log->{
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
        App.setRoot("adminTransLog");
    }
}
