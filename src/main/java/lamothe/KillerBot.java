package lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.SimpleBot;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;
import com.coveo.blitz.client.dto.Move;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olivier on 2016-01-09.
 */
public class KillerBot implements SimpleBot {
    private static final Logger logger = LogManager.getLogger(KillerBot.class);
    public static boolean inBound(GameState.Position pos, GameState state) {
        return pos.getX() >= 0 && pos.getX() < state.getGame().getBoard().getSize() &&
                pos.getY() >= 0 && pos.getY() < state.getGame().getBoard().getSize();
    }

    private BotMove previous = BotMove.STAY;

    @Override
    public BotMove move(GameState gameState) {
        this.previous = move_test(gameState);

        return this.previous;
    }

    public BotMove move_test(GameState gameState) {
        //Initial computation on the game state
        TilePos[][] tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        DjikistraPath paths = new DjikistraPath(tiles);
        paths.calculate(gameState.getHero().getSpawnPos());

        GameState.Position nextPos = paths.getNextPosForBestMine(tiles[gameState.getHero().getPos().getX()][gameState.getHero().getPos().getY()], gameState.getHero().getId());

        return paths.findDirection(gameState.getHero().getPos(), nextPos);
    }

    public BotMove randomNumber(BotMove except) {
        BotMove move = randomNumber();
        while(move ==  except) {
            move = randomNumber();
        }

        return move;
    }

    public static BotMove inverse(BotMove move) {
        if(move == BotMove.EAST) {
            return BotMove.WEST;
        } else if(move == BotMove.WEST) {
            return BotMove.EAST;
        } else if(move == BotMove.NORTH) {
            return BotMove.SOUTH;
        } else if(move == BotMove.SOUTH) {
            return BotMove.NORTH;
        } else {
            return BotMove.STAY;
        }
    }

    public BotMove randomNumber() {
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

    public void getMines(DjikistraPath paths){

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
