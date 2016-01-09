package com.coveo.blitz.client.bot;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coveo.blitz.client.dto.GameState;

/**
 * Example bot
 */
public class RandomBot implements SimpleBot {
	private static final Logger logger = LogManager.getLogger(RandomBot.class);

    private BoardParser parser = new BoardParser();

    @Override
    public BotMove move(GameState gameState) {
    	logger.info(gameState.getHero().getPos().toString());

        List<Tile> tiles = parser.parse(gameState.getGame().getBoard().getTiles());
        logger.info(tiles);

        int randomNumber = (int)(Math.random() * 5);
        switch(randomNumber) {
            case 1:
            	logger.info("Going north");
                return BotMove.NORTH;
            case 2:
            	logger.info("Going south");
                return BotMove.SOUTH;
            case 3:
            	logger.info("Going east");
                return BotMove.EAST;
            case 4:
            	logger.info("Going west");
                return BotMove.WEST;
            default:
            	logger.info("Going nowhere");
                return BotMove.STAY;
        }
    }

    @Override
    public void setup() {
    }

    @Override
    public void shutdown() {
    }
}
