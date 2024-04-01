package oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {  
    public float totalHarga = 2.0f;
    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        // scene = new Scene(getFXML("login").load());
        scene = new Scene(getFXML("admin").load());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/logo1.png")));
        stage.setTitle("My Kasir");
        stage.setScene(scene);
        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(getFXML(fxml).load());
    }

    static void setRootWithParent(Parent parent) throws IOException {
        scene.setRoot(parent);
    }
    
    // Deprecated
    // private static Parent loadFXML(String fxml) throws IOException {
    //     FXMLLoader fxmlLoader = getFXML(fxml);
    //     return fxmlLoader.load();
    // }

    static FXMLLoader getFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    static Font Montserrat(double fontSize){
        Font font = Font.loadFont(App.class.getResourceAsStream("fonts/Montserrat.ttf"), fontSize);
        // Font font = Font.font("fonts/Montserrat.ttf", null, null, fontSize);
        // font = font.font("Montserrat", null, null, fontSize);
        
        // System.out.println(font);
        // System.out.println(font.getStyle());
        // System.out.println(App.class.getResourceAsStream("fonts/Montserrat.ttf"));
        return font;
    }

    public static void main(String[] args) {
        launch();
    }

}