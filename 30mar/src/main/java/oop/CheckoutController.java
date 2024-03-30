package oop;

import java.io.IOException;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
// import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckoutController {
    
    @FXML
    private TextField total_price;

    private float totalPayment;

    public void setTotalPayment(float total) {
        // this.totalPayment = total;
        // total_price.setText(Float.toString(totalPayment));
        total_price.setText(Float.toString(total));
        // total_price.setText("ok");
    }
    
    public void initialize(){
        // System.out.println(App.getHarga());
        // System.out.println(App.totalHarga);
        // total_price.setText(Float.toString(App.totalHarga));
    }

    @FXML
    private void batal(ActionEvent event) throws IOException{
        App.setRoot("login");
    }

    @FXML
    private void payment(ActionEvent event) throws IOException{
        App.setRoot("checkout");
    }
        

    

}