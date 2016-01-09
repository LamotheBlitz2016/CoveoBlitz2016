package lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.SimpleBot;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;
import com.coveo.blitz.client.dto.Move;
import lamothe.Strategy.KillStrategy;
import lamothe.Strategy.Strategy;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
