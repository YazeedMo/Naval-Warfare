package com.example.navalwarfare;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Ship {

    private final String shipName;
    private final ArrayList<Button> shipButtons;

    public Ship(String shipName, ArrayList<Button> shipButtons) {
        this.shipName = shipName;
        this.shipButtons = shipButtons;
    }

    // Checks if ship has been hit
    public String checkStatus(Button buttonGuess) {

        String status = "miss";

        for (Button shipButton : shipButtons) {
            if (shipButton == buttonGuess) {

                editButton(buttonGuess);
                GuiController.getButtonList().remove(buttonGuess);
                shipButtons.remove(buttonGuess);
                status = "hit";

                if (shipButtons.isEmpty()) {
                    status = "sunk";
                }
                break;
            }
        }
        return status;
    }

    // Change button appearance
    private void editButton(Button button) {

        BackgroundFill backgroundFill = new BackgroundFill(Color.RED, null, null);
        Background background = new Background(backgroundFill);
        button.setBackground(background);

    }

    // Getters and setters
    public String getShipName() {
        return this.shipName;
    }

}
