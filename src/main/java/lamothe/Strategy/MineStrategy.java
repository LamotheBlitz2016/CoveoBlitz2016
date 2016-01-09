package lamothe.Strategy;

import com.coveo.blitz.client.bot.BotMove;
import lamothe.ComputedContext;
import lamothe.DjikistraPath;
import lamothe.TilePos;

import java.util.Optional;

/**
 * Created by olivier on 2016-01-09.
 */
public class MineStrategy extends Strategy {

    public MineStrategy(ComputedContext context) {
        super(context);
    }

    @Override
    public BotMove getMove() {
        Optional<TilePos> mineTile = context.getPaths().getNextPosForBestMine(context.getHeroTile(),context.getGameState().getHero().getId());
        if(mineTile.isPresent()){
            return DjikistraPath.findDirection(context.getHeroTile(), mineTile.get());
        }
        else {
            return new BeerStrategy(context).getMove();
        }
    }
}
