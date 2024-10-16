package org.fluid.fluid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FLuiD extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FLuiD.class.getResource("FLuiD.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 350);
        Image image = new Image(String.valueOf(FLuiD.class.getResource("/Images/DB.png")));
        stage.setTitle("FLuiD");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(image);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}