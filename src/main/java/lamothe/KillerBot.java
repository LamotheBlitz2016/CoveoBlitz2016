package lamothe;

import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.SimpleBot;
import com.coveo.blitz.client.dto.GameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by olivier on 2016-01-09.
 */
public class KillerBot implements SimpleBot {
    private static final Logger logger = LogManager.getLogger(KillerBot.class);

    private ComputedContext context;

    @Override
    public BotMove move(GameState gameState) {
        context = new ComputedContext(gameState);
        return context.getSuitableStrategy().getMove();
    }

    @Override
    public void setup() {

    }

    @Override
    public void shutdown() {

    }
}
