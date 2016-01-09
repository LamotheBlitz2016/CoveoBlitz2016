package lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.SimpleBot;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;
import com.coveo.blitz.client.dto.Move;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by olivier on 2016-01-09.
 */
public class KillerBot implements SimpleBot {
    private static final Logger logger = LogManager.getLogger(KillerBot.class);

    @Override
    public BotMove move(GameState gameState) {
        logger.info("Parsing map");
        TilePos[][] tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        logger.info("Parsed map");

        DjikistraPath paths = new DjikistraPath(tiles);
        paths.calculate(gameState.getHero().getPos());


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

    private Move getBestMoveTowardsPosition(GameState.Position position){
        return null;
    }
}
