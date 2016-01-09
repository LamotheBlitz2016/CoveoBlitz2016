package lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.dto.GameState;
import lamothe.Strategy.BeerStrategy;
import lamothe.Strategy.KillStrategy;
import lamothe.Strategy.MineStrategy;
import lamothe.Strategy.Strategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by olivier on 2016-01-09.
 */
public class ComputedContext {

    private static final Logger LOGGER = LogManager.getLogger(ComputedContext.class);
    private enum StrategyType {
        BEER, KILL, MINE, NONE
    }
    private static StrategyType LAST_STRATEGY = StrategyType.NONE;
    private GameState gameState;
    private PathFinder paths;
    private BoardTile heroTile;
    private BoardTile[][] tiles;

    public ComputedContext(GameState gameState){
        this.gameState = gameState;
        tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        heroTile = tiles[gameState.getHero().getPos().getX()][gameState.getHero().getPos().getY()];
        paths = new PathFinder(tiles);
        paths.calculate(gameState.getHero().getPos());
    }

    public Strategy getSuitableStrategy(){

        Optional<GameState.Hero> killWorthyOpponent = gameState.getGame().getHeroes().stream().filter(
                x -> x.getMineCount() > gameState.getHero().getMineCount() && x.getLife() < gameState.getHero().getLife() && paths.getBestPath(heroTile, tiles[x.getPos().getX()][x.getPos().getY()]).size()  < 5 //Target rich/weak players/close
        ).findFirst();

        if(killWorthyOpponent.isPresent()){
            LOGGER.log(Level.INFO, "Using kill strategy");
            LAST_STRATEGY = StrategyType.KILL;
            return new KillStrategy(this, killWorthyOpponent.get());
        }

        if(gameState.getHero().getLife() > 30 && LAST_STRATEGY != StrategyType.BEER) {
            LOGGER.log(Level.INFO, "Using mine strategy");
            LAST_STRATEGY = StrategyType.MINE;
            return new MineStrategy(this);
        } else {
            LOGGER.log(Level.INFO, "Using beer strategy");
            LAST_STRATEGY = LAST_STRATEGY == StrategyType.BEER && gameState.getHero().getLife() > 75 ? StrategyType.NONE : StrategyType.BEER;
            return new BeerStrategy(this);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public BoardTile getHeroTile() {
        return heroTile;
    }

    public PathFinder getPaths() {
        return paths;
    }

    public BoardTile[][] getTiles() {
        return tiles;
    }
}
