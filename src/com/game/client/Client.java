package com.game.client;

import com.game.controller.Game;
import java.net.URISyntaxException;

public class Client {
    public static void main(String[] args) throws URISyntaxException {
        Game game = new Game();
        game.start();
    }
}
