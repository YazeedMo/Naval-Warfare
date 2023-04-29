package com.example.navalwarfare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavalWarfareApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NavalWarfare.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Naval Warfare");
        stage.setScene(scene);
        stage.show();

    }
}
