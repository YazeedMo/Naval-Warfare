package com.example.navalwarfare;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GuiController {

    // Gui components
    @FXML
    private GridPane boardGrid;
    @FXML
    private Label guessNumberLabel;
    @FXML
    private Label remainingShipsLabel;
    @FXML
    private Label notificationLabel;
    @FXML
    private Button restartButton;

    private static ArrayList<Button> buttonList;

    private Game game;

    @FXML
    private void initialize() {

        setBoardGridAxis();

        setButtons();

        displayInstructions();

        game = Game.getInstance();

    }

    // Places alphanumeric coordinates on grid axis
    private void setBoardGridAxis() {

        // Letters
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i < 11; i++) {
            Label letterLabel = createLabel(alphabet[i - 1]);
            boardGrid.add(letterLabel, 0, i);
        }

        // Numbers
        for (int i = 1; i < 11; i++) {
            Label numberLabel = createLabel(String.valueOf(i));
            boardGrid.add(numberLabel, i, 0);
        }

    }

    // Fills boarderGrid with buttons
    private void setButtons() {

        buttonList = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button button = createButton();
                boardGrid.add(button, j, i);
                buttonList.add(button);
            }
        }
    }

    // Shows instructions
    private void displayInstructions() {

        notificationLabel.setText("""
                There are 5 ships:
                Destroyer:\t 2 blocks
                Submarine:\t 3 blocks
                Cruiser:\t\t 3 blocks
                Battleship:\t 4 blocks
                Aircraft Carrier: 5 blocks
                
                Sink all ships to win.
                GoodLuck!!!""");

    }

    // Creates label
    private Label createLabel(String str) {

        Label label = new Label(str);

        // Size
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);

        // Layout
        label.setAlignment(Pos.CENTER);

        return label;

    }

    // Creates button
    private Button createButton() {

        Button button = new Button();

        // Size
        button.setMaxHeight(Double.MAX_VALUE);
        button.setMaxWidth(Double.MAX_VALUE);

        // Color
        Color color = Color.LIGHTBLUE;
        BackgroundFill backgroundFill = new BackgroundFill(color, null, null);
        Background background = new Background(backgroundFill);
        button.setBackground(background);

        // Border
        Color borderColor = Color.BLACK;
        BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.SOLID;
        CornerRadii cornerRadii = new CornerRadii(1);
        BorderWidths borderWidths = new BorderWidths(1);
        BorderStroke borderStroke = new BorderStroke(borderColor, borderStrokeStyle, cornerRadii, borderWidths);
        Border border = new Border(borderStroke);
        button.setBorder(border);

        // Action handler
        buttonClicked buttonClicked = new buttonClicked();
        button.setOnAction(buttonClicked);

        return button;
    }

    // Button EventHandler
    private class buttonClicked implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Button buttonClicked = (Button) event.getSource();
            game.checkUserGuess(buttonClicked);

            updateGui();

            // Check if all ships are dead
            if (game.getShipsRemaining() == 0) {
                endGame();
            }
        }
    }

    // Updates all Gui components
    private void updateGui() {

        guessNumberLabel.setText(String.valueOf(game.getNumGuess()));
        remainingShipsLabel.setText(String.valueOf(game.getShipsRemaining()));
        notificationLabel.setText(game.getNotification());

    }

    // End of game
    private void endGame() {

        // Disable all buttons
        ObservableList<Node> children = boardGrid.getChildren();
        for (Node node : children) {
            if (node instanceof Button gridButton) {
                gridButton.setDisable(true);
            }
        }

        updateGui();

        restartButton.setVisible(true);
        restartButton.setDisable(false);

    }

    // Restarts the game
    @FXML
    private void restartGame(ActionEvent event) throws IOException {

        // Reset the stage and scene
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();

        game.restartGame(stage);

    }

    // Getters and setters
    public static ArrayList<Button> getButtonList() {
        return buttonList;
    }

}
