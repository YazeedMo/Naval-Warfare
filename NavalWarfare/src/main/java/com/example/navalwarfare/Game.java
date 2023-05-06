package com.example.navalwarfare;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Made using Singleton pattern
public class Game {

    // Singleton only class
    private static Game instance;

    // Optional bot for testing
    private final Bot tester = new Bot();

    private ArrayList<Ship> shipsList;
    private int shipsRemaining;
    private int numGuess;
    private String notification;

    // Singleton private constructor
    private Game() {
        setUpGame();
    }

    // Singleton get only instance
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // Creates and places ships on board
    public void setUpGame() {

        ShipPlacer shipPlacer = new ShipPlacer();

        shipsList = new ArrayList<>();
        numGuess = 0;

        Ship ship1 = new Ship("Aircraft Carrier", Color.ORANGE, shipPlacer.assignShipButtons(5));
        Ship ship2 = new Ship("Battleship", Color.RED, shipPlacer.assignShipButtons(4));
        Ship ship3 = new Ship("Destroyer", Color.DARKBLUE, shipPlacer.assignShipButtons(3));
        Ship ship4 = new Ship("Submarine", Color.GREEN, shipPlacer.assignShipButtons(3));
        Ship ship5 = new Ship("Patrol Boat", Color.PURPLE, shipPlacer.assignShipButtons(2));

        shipsList.add(ship1);
        shipsList.add(ship2);
        shipsList.add(ship3);
        shipsList.add(ship4);
        shipsList.add(ship5);

        shipsRemaining = shipsList.size();
        numGuess = 0;
        notification = "Sink all ships to win!";

        // Optional test loop
        test();

    }

    // Determine outcome of player guess
    public void checkUserGuess(Button button) {

        // If button has not yet been clicked, check if any ships have been affected
        if (button.getUserData() == null || !button.getUserData().equals("Clicked")) {
            setButtonColor(button);
            button.setUserData("Clicked");
            numGuess++;
            notification = checkShips(button);
            if (shipsRemaining == 0 ) {
                notification = "All ships are dead!\n" +
                        "It took you " + numGuess + " guesses.";
            }
        }
        else {
            notification = "Already clicked";
        }
    }

    // Set button color
    private void setButtonColor(Button button) {

        BackgroundFill backgroundFill = new BackgroundFill(Color.GRAY, null, null);
        Background background = new Background(backgroundFill);
        button.setBackground(background);

    }

    // Checks status of ships
    private String checkShips(Button button) {

        String result = "miss";

        for (Ship ship : shipsList) {

            String shipStatus = ship.checkStatus(button);

            if (!shipStatus.equals("miss")) {
                if (shipStatus.equals("hit")) {
                    result = "You hit " + ship.getShipName();
                    break;
                }
                else {
                    shipsRemaining--;
                    result = ship.getShipName() + " has sunk!";
                }
                break;
            }
        }
        return result;
    }

    // Displays result when games ends
    public void endGame() throws IOException {

    }

    // Restarts the game
    public void restartGame(Stage stage) throws IOException {

        // Reset Game instance
        instance = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Optional test method
    public void test() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(tester);
        executor.shutdown();

    }

    // region GETTERS AND SETTERS

    // Getters and setters
    public int getShipsRemaining() {
        return this.shipsRemaining;
    }

    public int getNumGuess() {
        return this.numGuess;
    }

    public String getNotification() {
        return this.notification;
    }

    public ArrayList<Ship> getShipsList() {
        return this.shipsList;
    }

    // endregion

}
