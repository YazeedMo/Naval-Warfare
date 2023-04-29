package com.example.navalwarfare;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GuiController {

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

    public static ArrayList<Button> buttonList = new ArrayList<>();

    private Game game;

    @FXML
    private void initialize() {

        setBoardGridAxis();

        setButtons();

        displayInstructions();

        game = new Game();
        game.startGame();

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

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button button = createButton();
                boardGrid.add(button, j, i);
                buttonList.add(button);
            }
        }
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
        button.setOnAction(e -> {
            String result = game.checkUserGuess((Button) e.getSource());
            notificationLabel.setText(result);
            remainingShipsLabel.setText(String.valueOf(Game.shipsList.size()));
            guessNumberLabel.setText(String.valueOf(Game.numGuess));

            // Check if all ships are sunk
            if (Integer.parseInt(remainingShipsLabel.getText()) == 0) {

                // Disable all buttons
                ObservableList<Node> children = boardGrid.getChildren();

                for (Node node : children) {
                    if (node instanceof Button gridButton) {
                        gridButton.setDisable(true);
                    }
                }

                notificationLabel.setText("All ships have sunk!\n" +
                        "It took you " + guessNumberLabel.getText() + " moves");

                restartButton.setVisible(true);
                restartButton.setDisable(false);
            }

        });

        return button;

    }

    // Shows instructions
    private void displayInstructions() {

        notificationLabel.setText("""
                There are 5 ships:
                Destroyer: 2 blocks
                Submarine: 3 blocks
                Cruiser: 3 blocks
                Battleship: 4 blocks
                Aircraft Carrier: 5 blocks
                
                Sink all ships to win.
                GoodLuck!!!""");

    }

    // Restarts the game
    @FXML
    private void restartGame(ActionEvent event) throws IOException {

        game.endGame();

        // Reset the stage and scene
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();

        NavalWarfareApplication navalWarfareApplication = new NavalWarfareApplication();
        navalWarfareApplication.start(stage);

    }

}
