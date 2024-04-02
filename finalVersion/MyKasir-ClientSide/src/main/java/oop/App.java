package oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaFX App
 */

public class App extends Application {
    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getFXML("login").load());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/logo1.png")));
        stage.setTitle("My Kasir");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXML(fxml).load());
    }

    static void setRootWithParent(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    static FXMLLoader getFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    static Font Montserrat(double fontSize){
        Font font;
        font = Font.loadFont(App.class.getResourceAsStream("fonts/montserrat/Montserrat-Bold.ttf"), fontSize);
        return font;
    }

    static String getCurrentDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        return dtf.format(time).toString();
    }

    static String intThousandSeparator(int x){
        return String.format("%,d", x);
    }
    static String floatThousandSeparator(float x){
        String y = Float.toString(x).replace(".0","");
        return String.format("%,d", Integer.parseInt(y)) + ".0";
    }

    @SuppressWarnings("exports")
    public void logout(Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to exit.");
        alert.setContentText("Do you want to log off before exiting?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }

    }
    public static void main(String[] args) {
        launch();
    }
}