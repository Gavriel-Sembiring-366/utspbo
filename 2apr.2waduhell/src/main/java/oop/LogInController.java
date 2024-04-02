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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.time.LocalDateTime;
// import java.sql.Statement;
// import java.time.format.DateTimeFormatter;

public class LogInController {
    
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
        FXMLLoader loader = App.getFXML("login");
        Parent parent = loader.load();
        LogInController loginCtrl = loader.getController();
        loginCtrl.operationSwap( textTitle.getText());
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
            // Statement stmt = DBConnector.connect.createStatement();
            // String sql = "SELECT * FROM user WHERE username LIKE '" + username + "';";
            // ResultSet rs = stmt.executeQuery(sql);
            PreparedStatement psmt = DBConnector.connect.prepareStatement(
                "SELECT * FROM user WHERE username LIKE ?;"
            );
            psmt.setString(1, username);
            ResultSet rs = psmt.executeQuery();

            if(opt.equals("Log In") || opt.equals("Log In As Admin")){
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
    
    private int getUserId(String username) throws SQLException{
        PreparedStatement psmt = DBConnector.connect.prepareStatement(
            "SELECT * FROM user WHERE username LIKE ?"
        );
        psmt.setString(1, username);
        ResultSet rs = psmt.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    @FXML
    public void actionLogIn() throws IOException, SQLException {
        String actionType =  textTitle.getText();
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        // LocalDateTime time = LocalDateTime.now();

        boolean isValid = userValidator(username, password, actionType);
        System.out.println("valid : " + isValid);

        PreparedStatement psmt;
        ResultSet rs;


        if (isValid){
            if(actionType.equals("Log In")){
                // psmt = DBConnector.connect.prepareStatement(
                //     "SELECT * FROM user WHERE username LIKE ?"
                // );
                // psmt.setString(1, username);
                // rs = psmt.executeQuery();
                // rs.next();
                // int _userIdFromDb = rs.getInt("id");
                int _userIdFromDb = getUserId(username);
                ActivityLogDB.insertActivityLog(new ActivityLog(_userIdFromDb, App.getCurrentDateTime().toString(), username, "LI"));
                // App.setRoot("counter");
                FXMLLoader loader = App.getFXML("counter");
                Parent parent = loader.load();
                CounterController ctrl = loader.getController();
                ctrl.setCurrentUser(new UserIdentity(username, _userIdFromDb, "user"));
                App.setRootWithParent(parent);
            }
            else if(actionType.equals("Sign In")){
                System.out.println("Account successfully made");
                // Statement stmt = DBConnector.connect.createStatement();
                // String sql = "INSERT INTO user (username, password) VALUES("+ "'" + username+ "', " + password.hashCode() +")";
                // stmt.executeUpdate(sql);
                psmt = DBConnector.connect.prepareStatement(
                    "INSERT INTO user (username, password, role) VALUES(?, ?, ?)"
                );
                psmt.setString(1, username);
                psmt.setInt(2, password.hashCode());
                psmt.setString(3, "user");
                // String sql = "INSERT INTO user (username, password) VALUES("+ "'" + username+ "', " + password.hashCode() +")";
                psmt.executeUpdate();
                int _userIdFromDb = getUserId(username);
                ActivityLogDB.insertActivityLog(new ActivityLog(_userIdFromDb, App.getCurrentDateTime(), username, "AC"));
                _actionSwap();
            }
            else if(actionType.equals("Log In As Admin")){
                psmt = DBConnector.connect.prepareStatement(
                    "SELECT role FROM user WHERE username LIKE ?"
                );
                psmt.setString(1, username);
                rs = psmt.executeQuery();
                if(rs.next() && (rs.getString("role").equals("admin"))){
                    int _userIdFromDb = getUserId(username);
                    ActivityLogDB.insertActivityLog(new ActivityLog(_userIdFromDb, App.getCurrentDateTime(), username, "LI"));
                    App.setRoot("admin");
                }
                // else{
                //     App.setRoot("counter");
                // }
                // App.setRoot("admin");
            }
        }
    }

    // @FXML
    // private void actiongotoadmin() throws IOException {
    //     FXMLLoader loader = App.getFXML("login_admin");
    //     Parent parent = loader.load();
    //     App.setRootWithParent(parent);
    // }

    @FXML
    private void actionSwap() throws IOException {
        _actionSwap();
    }

}
