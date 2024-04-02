package oop;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CheckoutController {
    
    @FXML private TextField textFieldCost;
    @FXML private TextField textFieldPay;
    @FXML private TextField textFieldChange;
    @FXML private Label error_pay;
    @FXML private Label labelHarga;
    @FXML private Label labelPay;
    @FXML private Label labelChange;
    @FXML private Text textTitle;
    @FXML private Button buttonBack;
    @FXML private Button buttonBayar;

    UserIdentity currentUser;

    public void initialize(){
        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldCost,
            textFieldPay,
            textFieldChange
        );
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelHarga,
            labelPay,
            labelChange
        );
        ObservableList<Button> buttons = FXCollections.observableArrayList(
            buttonBack,
            buttonBayar
        );

        textFields.forEach(textField -> {textField.fontProperty().set(App.Montserrat(12));});
        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14));});
        buttons.forEach(button -> {button.fontProperty().set(App.Montserrat(18));});
        textTitle.fontProperty().set(App.Montserrat(56));

        error_pay.setTextFill(Color.RED);
    }

    public void setCurrentUser(UserIdentity ui){
        currentUser = ui;
    }

    public void setTotalPayment(float total) {
       textFieldCost.setText(Float.toString(total));
    }

    private boolean calculateChange(){
        try {
            float getChange = Float.parseFloat(textFieldPay.getText().replaceAll(",", "")) - Float.parseFloat(textFieldCost.getText());
            if(getChange < 0)
            {
                error_pay.setText("Not enough cash, strangers.");
                textFieldChange.setText("");
            }
            else
            {
                textFieldChange.setText(Float.toString(getChange));
                error_pay.setText("");
                return true;
            }
        }
        catch (NumberFormatException numError)
        {
            error_pay.setText("Kindly give proper input");
        }
        return false;
    }

    @FXML
    private void batal(ActionEvent event) throws IOException{
        FXMLLoader loader = App.getFXML("counter");
        Parent parent = loader.load();
        CounterController check = loader.getController();
        check.setCurrentUser(currentUser);
        ActivityLogDB.insertActivityLog(new ActivityLog(currentUser.getUserId(), App.getCurrentDateTime(), currentUser.getUserName(), "TR NO"));
        App.setRootWithParent(parent);
    }

    @FXML
    private void payment(ActionEvent event) throws IOException{
        if (calculateChange() == true){
            ActivityLogDB.insertActivityLog(new ActivityLog(currentUser.getUserId(), App.getCurrentDateTime(), currentUser.getUserName(), "TR YES"));
            currentUser.getItemList().forEach(item->{
                TransactionLogDB.insertTransactionLog(new TransactionLog(currentUser.getUserId(), App.getCurrentDateTime(), currentUser.getUserName(), item.table_nama, item.table_harga));
            });
            FXMLLoader loader = App.getFXML("receipt");
            Parent parent = loader.load();
            ReceiptController ctrl = loader.getController();
            ctrl.setCurrentUser(currentUser);
            ctrl.loadTable();
            float _pay = Float.parseFloat(textFieldPay.getText().replaceAll(",", "")) ;
            float _change = _pay - Float.parseFloat(textFieldCost.getText());
            ctrl.loadInformation(_pay, _change);
            App.setRootWithParent(parent);
        }
    }

    @FXML
    private void countChange(ActionEvent event){
        calculateChange();
    }
    
}