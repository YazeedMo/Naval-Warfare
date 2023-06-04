package com.example.navalwarfare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    @FXML
    private Button buttonPlay;

    @FXML
    void buttonPlayClicked(ActionEvent event) throws IOException {

        Game game = Game.getInstance();

        Stage stage = getStage(event);
        game.restartGame(stage);

    }

    // Get current stage
    private Stage getStage(ActionEvent event) {

        return (Stage) (((Node) (event.getSource())).getScene().getWindow());

    }

}
