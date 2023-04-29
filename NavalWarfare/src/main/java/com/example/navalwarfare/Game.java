package com.example.navalwarfare;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Game {

    public static ArrayList<Ship> shipsList = new ArrayList<>();
    public static int numGuess = 0;

    private final ShipPlacer shipPlacer = new ShipPlacer();

    public void startGame() {

        setUpGame();
        // get button clicks and check all ship statuses
        // track number of guesses

    }

    // Creates ships
    private void setUpGame() {

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

        String result = "miss";

        if (button.getUserData() == null || !button.getUserData().equals("Clicked")) {

            numGuess++;

            button.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

            for (Ship ship : shipsList) {
                String shipStatus = ship.checkStatus(button);

                if (!shipStatus.equals("miss")) {
                    if (shipStatus.equals("hit")) {
                        result = "You hit " + ship.getShipName();
                        break;
                    } else {
                        shipsList.remove(ship);
                        result = ship.getShipName() + " has sunk!";
                    }
                    break;
                }
            }
            button.setUserData("Clicked");
        }
        else {
            result = "Already clicked";
        }
        return result;
    }

    public void endGame() {

        // Reset all static variables
        Game.numGuess = 0;
        Game.shipsList.clear();

        GuiController.buttonList.clear();

    }


}
