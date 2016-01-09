package com.coveo.blitz.client.bot;

import com.coveo.blitz.client.dto.GameState;

/**
 * Most basic interface for a bot
 * <p/>
 * The SimpleBot gets a GameState and is expected to return a BotMove.  The response to the server is a Move,
 * but since a SimpleBot does not know its API key, it returns a BotMove to indicate the direction and allows the framework
 * to take care of building a Move response.
 * <p/>
 * The bot must handle its own map parsing, threading, timing, etc.
 */
public interface SimpleBot {

    /**
     * Method that plays each move
     *
     * @param gameState the current game state
     * @return the decided move
     */
    public BotMove move(GameState gameState);

    /**
     * Called before the game is started
     */
    public void setup();

    /**
     * Called after the game
     */
    public void shutdown();
}
