package oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInController {
    
    @FXML private Text textHeader;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private Hyperlink noAcc;
    @FXML private Button buttonLogin;

    @FXML
    public void actionLogIn() throws IOException, SQLException {
        String actionType = textHeader.getText();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        boolean isValid = userValidator(username, password, actionType);
        System.out.println("valid : " + isValid);

        Statement stmt = DBConnector.connect.createStatement();
        if (isValid){
            if(actionType.equals("Log In")){
                App.setRoot("counter");
            }
            else if(actionType.equals("Sign In")){
                System.out.println("Account successfully made");
                String sql = "INSERT INTO user (username, password) VALUES("+ "'" + username+ "', " + password.hashCode() +")";
                stmt.executeUpdate(sql);
                _actionSwap();
            }
        }
    }

    private void _actionSwap() throws IOException {
        FXMLLoader loader = App.getFXML("login");
        Parent parent = loader.load();
        LogInController loginCtrl = loader.getController();
        loginCtrl.operationSwap(textHeader.getText());
        App.setRootWithParent(parent);
    }

    @FXML
    private void actionSwap() throws IOException {
        _actionSwap();
    }

    private void operationSwap(String arg0){
        if( arg0.equals("Log In")){
            textHeader.setText("Sign In");
            buttonLogin.setText("Sign In");
            noAcc.setText("Already have account?");
        }
        // g guna cuma gpp
        else if( arg0.equals("Sign In")){
            textHeader.setText("Log In");
            buttonLogin.setText("Log In");
        }
    }

    private boolean userValidator(String username, String password, String opt)
    {
        DBConnector.initDBConnection();
        try {
            Statement stmt = DBConnector.connect.createStatement();
            String sql = "SELECT * FROM user WHERE username LIKE '" + username + "';";
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
}
