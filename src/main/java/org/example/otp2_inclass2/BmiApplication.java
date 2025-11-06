package org.example.otp2_inclass2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BmiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BmiApplication.class.getResource("bmiCalculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 370);
        stage.setTitle("BMI Calculator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
