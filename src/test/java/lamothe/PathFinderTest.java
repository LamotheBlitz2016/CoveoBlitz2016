package lamothe;

import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by olivier on 2016-01-09.
 */
public class PathFinderTest {

    @Test
    public void testFindDirectionX() throws Exception {

        BoardTile actual = new BoardTile(Tile.Air, new GameState.Position(0, 0));
        BoardTile dest = new BoardTile(Tile.MineNeutral, new GameState.Position(1, 0));

        BotMove move = PathFinder.findDirection(actual, dest);

        assertEquals(BotMove.SOUTH, move);
    }

    @Test
    public void testFindDirectionY() throws Exception {

        BoardTile actual = new BoardTile(Tile.Air, new GameState.Position(0, 0));
        BoardTile dest = new BoardTile(Tile.MineNeutral, new GameState.Position(0, 1));

        BotMove move = PathFinder.findDirection(actual, dest);

        assertEquals(BotMove.EAST, move);
    }
}