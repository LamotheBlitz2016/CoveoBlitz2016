package lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.dto.GameState;
import lamothe.Strategy.BeerStrategy;
import lamothe.Strategy.KillStrategy;
import lamothe.Strategy.MineStrategy;
import lamothe.Strategy.Strategy;

import java.util.Optional;

/**
 * Created by olivier on 2016-01-09.
 */
public class ComputedContext {

    private static enum StrategyType {
        BEER, KILL, MINE, NONE
    }
    private static StrategyType LAST_STRATEGY = StrategyType.NONE;
    private GameState gameState;
    private DjikistraPath paths;
    private TilePos heroTile;
    private TilePos[][] tiles;

    public ComputedContext(GameState gameState){
        this.gameState = gameState;
        tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles(), gameState.getGame().getBoard().getSize());
        heroTile = tiles[gameState.getHero().getPos().getX()][gameState.getHero().getPos().getY()];
        paths = new DjikistraPath(tiles);
        paths.calculate(gameState.getHero().getPos());
    }

    public Strategy getSuitableStrategy(){

        Optional<GameState.Hero> killWorthyOpponent = gameState.getGame().getHeroes().stream().filter(
                x -> x.getGold() > gameState.getHero().getGold() && x.getLife() < gameState.getHero().getLife() && paths.getBestPath(heroTile, tiles[x.getPos().getX()][x.getPos().getY()]).size() < 2 //Target rich/weak players/close
        ).findFirst();

        if(killWorthyOpponent.isPresent()){
            LAST_STRATEGY = StrategyType.KILL;
            return new KillStrategy(this, killWorthyOpponent.get());
        }

        if(gameState.getHero().getLife() > 50 && LAST_STRATEGY != StrategyType.BEER) {
            LAST_STRATEGY = StrategyType.MINE;
            return new MineStrategy(this);
        } else {
            LAST_STRATEGY = LAST_STRATEGY == StrategyType.BEER && gameState.getHero().getLife() > 75 ? StrategyType.NONE : StrategyType.BEER;
            return new BeerStrategy(this);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public TilePos getHeroTile() {
        return heroTile;
    }

    public DjikistraPath getPaths() {
        return paths;
    }

    public TilePos[][] getTiles() {
        return tiles;
    }
}
