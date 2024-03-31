package oop;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
// import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CheckoutController {
    
    @FXML private TextField cost;
    @FXML private TextField pay;
    @FXML private TextField change;
    @FXML private Label error_pay;

    public void setTotalPayment(float total) {
        cost.setText(Float.toString(total));
    }
    
    public void initialize(){
        error_pay.setTextFill(Color.RED);
    }

    private boolean calculateChange(){
        try {
            float getChange = Float.parseFloat(pay.getText().replaceAll(",", "")) - Float.parseFloat(cost.getText());
            if(getChange < 0)
            {
                // System.out.println("Not enough cash, strangers. \n      change = " + getChange);
                error_pay.setText("Not enough cash, strangers.");
                change.setText("");
            }
            else
            {
                change.setText(Float.toString(getChange));
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
        App.setRoot("login");
    }

    @FXML
    private void payment(ActionEvent event) throws IOException{
        if (calculateChange() == true){
            App.setRoot("receipt");
        }
    }

    @FXML
    private void countChange(ActionEvent event){
        calculateChange();
    }
    
}