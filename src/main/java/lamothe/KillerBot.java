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
        return moveMineStrat(gameState);
    }

    public BotMove moveMineStrat(GameState gameState) {
        //Initial computation on the game state

        TilePos[][] tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        TilePos heroTile = tiles[gameState.getHero().getPos().getX()][gameState.getHero().getPos().getY()];
        DjikistraPath paths = new DjikistraPath(tiles);
        paths.calculate(gameState.getHero().getPos());

        TilePos mineTile = paths.getNextPosForBestMine(heroTile,gameState.getHero().getId());

        return DjikistraPath.findDirection(heroTile, mineTile);
    }

    public BotMove move_test(GameState gameState) {
        //Initial computation on the game state
        TilePos[][] tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        DjikistraPath paths = new DjikistraPath(tiles);
        paths.calculate(gameState.getHero().getSpawnPos());

        List<TilePos> p = paths.getBestPath(tiles[gameState.getHero().getSpawnPos().getX()][gameState.getHero().getSpawnPos().getY()],
                tiles[gameState.getHero().getSpawnPos().getX() + 3][gameState.getHero().getSpawnPos().getY() + 3]);

        //TODO: Define and sort possible objectives

        //TODO: Decision making

/*        List<GameState.Position> neighbours = new ArrayList<>();
        GameState.Position ouest = new GameState.Position(gameState.getHero().getPos().getX(), gameState.getHero().getPos().getY() -1 );
        GameState.Position est = new GameState.Position(gameState.getHero().getPos().getX(), gameState.getHero().getPos().getY() +1 );
        GameState.Position sud  = new GameState.Position(gameState.getHero().getPos().getX() -1, gameState.getHero().getPos().getY() );
        GameState.Position nord = new GameState.Position(gameState.getHero().getPos().getX() +1, gameState.getHero().getPos().getY());

        if(inBound(sud, gameState) && tiles[sud.getX()][sud.getY()].getCurrentTile() == Tile.MineNeutral ||
                tiles[sud.getX()][sud.getY()].getCurrentTile() == Tile.MinePlayer1 ||
                tiles[sud.getX()][sud.getY()].getCurrentTile() == Tile.MinePlayer2 ||
                tiles[sud.getX()][sud.getY()].getCurrentTile() == Tile.MinePlayer3 ||
                tiles[sud.getX()][sud.getY()].getCurrentTile() == Tile.MinePlayer4 ){
            if(gameState.getHero().getPos().equals(sud)) {
                return inverse(this.previous);
            }

            return  BotMove.SOUTH;
        }

        if(inBound(nord, gameState) && tiles[nord.getX()][nord.getY()].getCurrentTile() == Tile.MineNeutral ||
                tiles[nord.getX()][nord.getY()].getCurrentTile() == Tile.MinePlayer1 ||
                tiles[nord.getX()][nord.getY()].getCurrentTile() == Tile.MinePlayer2 ||
                tiles[nord.getX()][nord.getY()].getCurrentTile() == Tile.MinePlayer3 ||
                tiles[nord.getX()][nord.getY()].getCurrentTile() == Tile.MinePlayer4 ){

            if(gameState.getHero().getPos().equals(nord)) {
                return inverse(this.previous);
            }
            return  BotMove.NORTH;
        }

        if(inBound(est, gameState) && tiles[est.getX()][est.getY()].getCurrentTile() == Tile.MineNeutral ||
                tiles[est.getX()][est.getY()].getCurrentTile() == Tile.MinePlayer1 ||
                tiles[est.getX()][est.getY()].getCurrentTile() == Tile.MinePlayer2 ||
                tiles[est.getX()][est.getY()].getCurrentTile() == Tile.MinePlayer3 ||
                tiles[est.getX()][est.getY()].getCurrentTile() == Tile.MinePlayer4 ){

            if(gameState.getHero().getPos().equals(est)) {
                return inverse(this.previous);
            }
            return  BotMove.EAST;
        }

        if(inBound(ouest, gameState) && tiles[ouest.getX()][ouest.getY()].getCurrentTile() == Tile.MineNeutral ||
                tiles[ouest.getX()][ouest.getY()].getCurrentTile() == Tile.MinePlayer1 ||
                tiles[ouest.getX()][ouest.getY()].getCurrentTile() == Tile.MinePlayer2 ||
                tiles[ouest.getX()][ouest.getY()].getCurrentTile() == Tile.MinePlayer3 ||
                tiles[ouest.getX()][ouest.getY()].getCurrentTile() == Tile.MinePlayer4 ){

            if(gameState.getHero().getPos().equals(ouest)) {
                return inverse(this.previous);
            }
            return  BotMove.WEST;
        }*/


        return randomNumber();
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
