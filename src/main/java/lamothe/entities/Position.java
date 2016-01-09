package lamothe.entities;

import com.coveo.blitz.client.bot.Tile;

/**
 * Created by jeremiep on 2016-01-09.
 */
public class Position {
    private Tile currentTile;
    private int x;
    private int y;

    public Position(Tile tile, int x, int y) {
        this.setCurrentTile(tile);
        this.setX(x);
        this.setY(y);
    }


    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
