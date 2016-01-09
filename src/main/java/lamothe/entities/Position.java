package lamothe.entities;

import com.coveo.blitz.client.bot.Tile;
import javafx.geometry.Pos;

/**
 * Created by jeremiep on 2016-01-09.
 */
public class Position {
    private Tile currentTile;
    private int x;
    private int y;

    public Position(Tile tile, int x, int y) {
        this.currentTile = tile;
        this.x = x;
        this.y = y;
    }
}
