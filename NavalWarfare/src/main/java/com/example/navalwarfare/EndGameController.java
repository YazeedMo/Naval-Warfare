package com.example.navalwarfare;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EndGameController {

    @FXML
    private Label guessNumberLabel;

    @FXML
    private Label ratingLabel;

    private final Game game = Game.getInstance();

    @FXML
    private void initialize() {

        int numGuesses = game.getNumGuess();

        guessNumberLabel.setText(String.valueOf(numGuesses));

        if (numGuesses == 17) {
            ratingLabel.setText("All the world's luck is on your side!!!!");
        }
        else if (numGuesses < 20) {
            ratingLabel.setText("Nah that was luck. Try again");
        }
        else if (numGuesses < 40) {
            ratingLabel.setText("Excellent");
        }
        else if (numGuesses < 60) {
            ratingLabel.setText("Very good");
        }
        else if (numGuesses < 80) {
            ratingLabel.setText("Average");
        }
        else {
            ratingLabel.setText("Faaiilure");
        }

    }

    @FXML
    void buttonRestartClicked(ActionEvent event) {

        // Get current stage
        Stage stage = (Stage) (((Node) (event.getTarget())).getScene().getWindow());
        // Get the game stage
        Stage gameStage = (Stage) (stage.getOwner());
        // Close current stage
        stage.close();

        try {
            game.restartGame(gameStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void buttonMainMenuClicked(ActionEvent event) throws IOException {

        Stage stage = getStage(event);

        // Get the game stage
        Stage gameStage = (Stage) (stage.getOwner());
        gameStage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();

    }

    // Get current stage
    private Stage getStage(ActionEvent event) {

        return (Stage) (((Node) (event.getSource())).getScene().getWindow());

    }


}
