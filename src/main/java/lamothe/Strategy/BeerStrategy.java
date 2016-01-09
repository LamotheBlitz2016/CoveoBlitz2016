package lamothe.Strategy;

import com.coveo.blitz.client.bot.BotMove;
import lamothe.ComputedContext;
import lamothe.DjikistraPath;
import lamothe.TilePos;

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
        Optional<TilePos> beerTile = context.getPaths().getNextPosForBestBeer(context.getHeroTile());
        if(beerTile.isPresent()){
            return DjikistraPath.findDirection(context.getHeroTile(), beerTile.get());
        }
        return BotMove.STAY;
    }
}
