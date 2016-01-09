package lamothe;

import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;

/**
 * Created by jeremiep on 2016-01-09.
 */
public class TilePos {
    private Tile currentTile;
    private GameState.Position currentPos;

    public TilePos(Tile tile, GameState.Position pos) {
        this.setCurrentTile(tile);
        this.setCurrentPos(pos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.currentPos.equals(o);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) - %s", currentPos.getX(), currentPos.getY(), currentTile);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public GameState.Position getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(GameState.Position currentPos) {
        this.currentPos = currentPos;
    }
}
