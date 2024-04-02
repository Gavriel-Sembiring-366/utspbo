package oop;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginAdminController {
    @FXML private Label labelTitle;
    @FXML private Text textTitle;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private Hyperlink noAcc;
    @FXML private Button buttonLogin;

    public void initialize(){
        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldUsername,
            textFieldPassword
        );
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelTitle
        );
        ObservableList<Button> buttons = FXCollections.observableArrayList(
            buttonLogin
        );

        textFields.forEach(textField -> {textField.fontProperty().set(App.Montserrat(24));});
        labels.forEach(label->{label.fontProperty().set(App.Montserrat(56));}); //14
        buttons.forEach(button->{button.fontProperty().set(App.Montserrat(18));});
        noAcc.fontProperty().set(App.Montserrat(13));
        textTitle.fontProperty().set(App.Montserrat(56));
    }

    private void _actionSwap() throws IOException {
        FXMLLoader loader = App.getFXML("login_admin");
        Parent parent = loader.load();
        LoginAdminController loginAdmCtrl = loader.getController();
        loginAdmCtrl.operationSwap( textTitle.getText());
        App.setRootWithParent(parent);
    }
    
    private void operationSwap(String arg0){
        if( arg0.equals("Log In")){
            textTitle.setText("Sign In");
            buttonLogin.setText("Sign In");
            noAcc.setText("Already have account?");
        }
        // g guna cuma gpp
        else if( arg0.equals("Sign In")){
             textTitle.setText("Log In");
            buttonLogin.setText("Log In");
        }
    }

    private boolean userValidator(String username, String password, String opt)
    {
        DBConnector.initDBConnection();
        try {
            Statement stmt = DBConnector.connect.createStatement();
            String sql = "SELECT * FROM admin WHERE username LIKE '" + username + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if(opt.equals("Log In")){
                return rs.next() && (password.hashCode() == rs.getInt("password"));
            }
            else if(opt.equals("Sign In")){
                return !rs.next();
            }
        }
        catch (SQLException sqlError)
        {
            System.out.println("sql error : " + sqlError);
        }
        return false;
    }
    
    @FXML
    public void actionLogIn() throws IOException, SQLException {
        String actionType =  textTitle.getText();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        boolean isValid = userValidator(username, password, actionType);
        System.out.println("valid : " + isValid);

        Statement stmt = DBConnector.connect.createStatement();
        if (isValid){
            if(actionType.equals("Log In")){
                App.setRoot("admin");
            }
            else if(actionType.equals("Sign In")){
                System.out.println("Account successfully made");
                String sql = "INSERT INTO admin (username, password) VALUES("+ "'" + username+ "', " + password.hashCode() +")";
                stmt.executeUpdate(sql);
                _actionSwap();
            }
        }
    }

    @FXML
    private void actionSwap() throws IOException {
        _actionSwap();
    }
}

