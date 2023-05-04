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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    @FXML
    private VBox shipStatusLabelVBox;

    private Game game;

    private static ArrayList<Button> buttonList;

    private ArrayList<Label> shipStatusLabels;

    @FXML
    private void initialize() {

        setBoardGridAxis();

        setButtons();

        game = Game.getInstance();

        setShipStatusLabels();

        updateGui();
    }

    // Places alphanumeric coordinates on grid axis
    private void setBoardGridAxis() {

        // Letters
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 1; i < 11; i++) {
            Label letterLabel = createBoardGridLabels(alphabet[i - 1]);
            boardGrid.add(letterLabel, 0, i);
        }

        // Numbers
        for (int i = 1; i < 11; i++) {
            Label numberLabel = createBoardGridLabels(String.valueOf(i));
            boardGrid.add(numberLabel, i, 0);
        }

    }

    // Fills boarderGrid with buttons
    private void setButtons() {

        buttonList = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                Button button = createBoardGridButtons();
                boardGrid.add(button, j, i);
                buttonList.add(button);
            }
        }
    }

    // Link each ship with a ship status label
    private void setShipStatusLabels() {

        // ArrayList that will store all labels
        shipStatusLabels = new ArrayList<>();

        // Get number of ships
        int numShips = game.getShipsList().size();

        // Create a label for each ship and link the label to its ship
        for (int i = 0; i < numShips; i++) {
            Label label = createShipStatusLabel();
            label.setUserData(game.getShipsList().get(i));  // Link ship to label
            shipStatusLabels.add(label);
            shipStatusLabelVBox.getChildren().add(label);  // Add label to GUI
        }

    }

    // Creates label
    private Label createBoardGridLabels(String str) {

        Label label = new Label(str);

        // Size
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);

        // Border
        Color borderColor = Color.BLACK;
        BorderStrokeStyle borderStrokeStyle = BorderStrokeStyle.SOLID;
        CornerRadii cornerRadii = new CornerRadii(1);
        BorderWidths borderWidths = new BorderWidths(1);
        BorderStroke borderStroke = new BorderStroke(borderColor, borderStrokeStyle, cornerRadii, borderWidths);
        Border border = new Border(borderStroke);
        label.setBorder(border);

        // Layout
        label.setAlignment(Pos.CENTER);

        return label;

    }

    // Creates button
    private Button createBoardGridButtons() {

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

    // Creates label for ship statuses
    private Label createShipStatusLabel() {

        Label label = new Label();

        // Font
        Font font = Font.font("System", FontWeight.BOLD, 15);
        label.setFont(font);

        return label;

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

        // shipsStatusLabels updates
        for (int i = 0; i < game.getShipsList().size(); i++) {

            Label label = shipStatusLabels.get(i);
            Ship ship = (Ship) shipStatusLabels.get(i).getUserData();

            Text shipName = new Text(ship.getShipName());
            shipName.setFont(Font.font("System", FontWeight.BOLD, 15));
            shipName.setFill(ship.getShipColor());
            label.setGraphic(shipName);
            label.setText(": " + ship.getShipSize());
        }

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

        // Get stage
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
