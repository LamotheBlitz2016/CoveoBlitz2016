package lamothe.Strategy;

import com.coveo.blitz.client.bot.BotMove;
import lamothe.ComputedContext;

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
