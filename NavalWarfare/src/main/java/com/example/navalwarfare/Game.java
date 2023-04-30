package com.example.navalwarfare;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Game {

    private ArrayList<Ship> shipsList;
    private int numGuess;

    private final ShipPlacer shipPlacer = new ShipPlacer();

    public void startGame() {

        setUpGame();

    }

    // Creates ships
    private void setUpGame() {

        shipsList = new ArrayList<>();
        numGuess = 0;

        Ship ship1 = new Ship("Aircraft Carrier", shipPlacer.assignShipButtons(5));
        Ship ship2 = new Ship("Battleship", shipPlacer.assignShipButtons(4));
        Ship ship3 = new Ship("Cruiser", shipPlacer.assignShipButtons(3));
        Ship ship4 = new Ship("Submarine", shipPlacer.assignShipButtons(3));
        Ship ship5 = new Ship("Destroyer", shipPlacer.assignShipButtons(2));

        shipsList.add(ship1);
        shipsList.add(ship2);
        shipsList.add(ship3);
        shipsList.add(ship4);
        shipsList.add(ship5);

    }

    public String checkUserGuess(Button button) {

        String result;

        if (button.getUserData() == null || !button.getUserData().equals("Clicked")) {
            numGuess++;
            setButtonColor(button);
            result = checkShips(button);
            button.setUserData("Clicked");
        }
        else {
            result = "Already clicked";
        }
        return result;
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
                    shipsList.remove(ship);
                    result = ship.getShipName() + " has sunk!";
                }
                break;
            }
        }
        return result;
    }

    public String gameOverMessage() {

        return "You sunk all ships!\n" +
                "It took you " + numGuess + " guesses.";

    }

    // Getters and setters
    public ArrayList<Ship> getShipsList() {
        return this.shipsList;
    }

    public int getNumGuess() {
        return this.numGuess;
    }


}
