package lamothe.Strategy;

import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.dto.GameState;
import lamothe.ComputedContext;
import lamothe.DjikistraPath;

/**
 * Created by olivier on 2016-01-09.
 */
public class KillStrategy extends Strategy {

    private GameState.Hero target;
    public KillStrategy(ComputedContext context, GameState.Hero target) {
        super(context);
        this.target = target;
    }

    @Override
    public BotMove getMove() {
        return DjikistraPath.findDirection(context.getHeroTile(), context.getPaths().getNextPosForHeroAttack(context.getHeroTile(), target));
    }


}
