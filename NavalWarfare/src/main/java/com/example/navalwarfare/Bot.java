package com.example.navalwarfare;

import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bot implements Runnable {

    @Override
    public void run() {
        try {
            play();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() throws IOException, InterruptedException {

        Random random = new Random();

        Game game = Game.getInstance();
        ArrayList<Button> buttonList = GuiController.getButtonList();
        int numButtons = buttonList.size();

        sleep(2000);

        for (int i = 0; i < 3; i++) {
            while (game.getShipsRemaining() > 0) {
                int randomIndex = random.nextInt(numButtons);
                Button randomButton = buttonList.get(randomIndex);
                if (randomButton.getUserData() == null || !randomButton.getUserData().equals("Clicked")) {
                    Platform.runLater(randomButton::fire);
                    sleep(100);
                }
            }
        }
    }

    private void sleep(int milliseconds) {

        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
