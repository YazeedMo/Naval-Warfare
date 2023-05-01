package com.example.navalwarfare;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ShipPlacer {

    private final int TOT_NUM_BUTTONS = GuiController.getButtonList().size();
    private final int HORIZONTAL_INCREMENT = 1;

    public ArrayList<Button> assignShipButtons(int shipSize) {

        Button[] finalShipButtons = new Button[shipSize];

        // Conditions for successful ship placement
        int maxAttempts = 200;
        int attempt = 0;
        boolean success = false;

        while (!success && (attempt < maxAttempts)) {

            finalShipButtons[0] = getRandomButton();
            int orientationIncrement = getOrientationIncrement();

            // Add rest of buttons (try catch because increment might add button index out of bounds)
            try {
                addRemainingButtons(finalShipButtons, orientationIncrement);
            }
            catch (IndexOutOfBoundsException e) {
                continue;
            }

            // Check if ship fits and if the button are available
            if (shipFits(finalShipButtons, orientationIncrement) && buttonsAvailable(finalShipButtons)) {
                finaliseButtons(finalShipButtons);
                success = true;
            }
            else {
                attempt++;
            }

        }
        return new ArrayList<>(Arrays.asList(finalShipButtons));
    }

    // Generate random button from buttonList
    private Button getRandomButton() {

        Random random = new Random();
        int randomIndex = random.nextInt(TOT_NUM_BUTTONS);
        return GuiController.getButtonList().get(randomIndex);

    }

    // Generates random ship orientation
    private int getOrientationIncrement() {

        Random random = new Random();
        int VERTICAL_INCREMENT = 10;
        int[] orientationList = {HORIZONTAL_INCREMENT, VERTICAL_INCREMENT};
        return orientationList[random.nextInt(orientationList.length)];

    }

    // Adds remaining buttons based on ship orientation
    private void addRemainingButtons(Button[] buttonList, int increment) {

        int buttonListSize = buttonList.length;

        Button firstButton = buttonList[0];
        int buttonListIndex = GuiController.getButtonList().indexOf(firstButton);

        for (int i = 1; i < buttonListSize; i++) {
            buttonListIndex = buttonListIndex + increment;
            Button nextButton = GuiController.getButtonList().get(buttonListIndex);
            buttonList[i] = nextButton;
        }

    }

    // Checks if ship fits properly based on ship orientation
    private boolean shipFits(Button[] buttonList, int increment) {

        Button firstButton = buttonList[0];
        Button lastButton = buttonList[buttonList.length - 1];

        if (increment == HORIZONTAL_INCREMENT) {
            // Check if first button index is on same row as last button index
            return (getRowNumber(firstButton) == getRowNumber(lastButton));
        }
        else {
            // Check if last button fits on grid
            int lastButtonIndex = GuiController.getButtonList().indexOf(lastButton);
            return (lastButtonIndex < GuiController.getButtonList().size());
        }
    }

    // Calculates row number based on index
    private int getRowNumber(Button button) {

        int buttonListIndex = GuiController.getButtonList().indexOf(button);
        return (buttonListIndex / 10) - 1;

    }

    // Checks if buttons are already taken by other ships
    private boolean buttonsAvailable(Button[] buttonList) {

        boolean available = true;

        for (Button button : buttonList) {
            if (button.getUserData() != null) {
                available = false;
                break;
            }
        }
        return available;
    }

    // Finalises the ship's buttons
    private void finaliseButtons(Button[] buttonList) {

        for (Button button : buttonList) {
            button.setUserData("Taken");
        }

    }
}
