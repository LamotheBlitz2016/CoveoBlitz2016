package lamothe.Strategy;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.dto.GameState;
import lamothe.ComputedContext;
import lamothe.DjikistraPath;
import lamothe.TilePos;

/**
 * Created by olivier on 2016-01-09.
 */
public abstract class Strategy {
    protected ComputedContext context;

    public Strategy(ComputedContext context){
        this.context = context;
    }
    public abstract BotMove getMove();
}
