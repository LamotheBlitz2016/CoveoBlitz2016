package lamothe.Strategy;

import com.coveo.blitz.client.bot.BotMove;
import lamothe.ComputedContext;
import lamothe.PathFinder;
import lamothe.BoardTile;

import java.util.Optional;

/**
 * Created by olivier on 2016-01-09.
 */
public class BeerStrategy extends Strategy {

    public BeerStrategy(ComputedContext context) {
        super(context);
    }

    @Override
    public BotMove getMove() {
        Optional<BoardTile> beerTile = context.getPaths().getNextPosForBestBeer(context.getHeroTile());
        if(beerTile.isPresent()){
            return PathFinder.findDirection(context.getHeroTile(), beerTile.get());
        }
        return BotMove.STAY;
    }
}
