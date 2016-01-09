package lamothe;

import com.coveo.blitz.client.dto.GameState;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by jeremiep on 2016-01-09.
 */
public class DjikistraPath {
    private TilePos[][] previous = null;
    private TilePos[][] map;

    public DjikistraPath(TilePos[][] map) {
        this.map = map;
        this.previous = new TilePos[map.length][map.length];
    }

    public void calculate(TilePos startingPoint) {
        this.previous = shortedPath(this.map, startingPoint);
    }

    private static TilePos[][] shortedPath(TilePos[][] map, TilePos startPoint) {
        int[][] dist = new int[map.length][map.length];
        TilePos[][] previous = new TilePos[map.length][map.length];
        LinkedList<TilePos> meh = new LinkedList<>();
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                dist[x][y] = Integer.MAX_VALUE;
                previous[x][y] = null;
                meh.push(map[x][y]);
            }
        }
        dist[startPoint.getCurrentPos().getX()][startPoint.getCurrentPos().getY()] = 0;

        while(!meh.isEmpty()) {
            TilePos u = meh.stream().sorted((p1, p2) -> Integer.compare(dist[p1.getCurrentPos().getX()][p1.getCurrentPos().getY()],
                    dist[p2.getCurrentPos().getX()][p2.getCurrentPos().getY()])).findFirst().get();
            meh.remove(u);

            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    if (dx != 0 || dy != 0) {
                        int neighbour_x = u.getCurrentPos().getX() + dx;
                        int neighbour_y = u.getCurrentPos().getY() + dy;
                        if(neighbour_x >= 0 && neighbour_x < map.length && neighbour_y >= 0 && neighbour_y < map.length) {
                            int alt = dist[u.getCurrentPos().getX()][u.getCurrentPos().getY()] + getDistanceFromTile(map[neighbour_x][neighbour_y]);
                            if(alt < dist[neighbour_x][neighbour_y]) {
                                dist[neighbour_x][neighbour_y] = alt;
                                previous[neighbour_x][neighbour_y] = u;
                            }
                        }
                    }
                }
            }
        }

        return previous;
    }

    public List<TilePos> getBestPath(TilePos[][] map, TilePos startPoint, TilePos endPoint) {
        int x = endPoint.getCurrentPos().getX();
        int y = endPoint.getCurrentPos().getY();
        List<TilePos> listPos = new LinkedList<>();

        while (!map[x][y].getCurrentPos().equals(startPoint.getCurrentPos())){
            x = previous[x][y].getCurrentPos().getX();
            y = previous[x][y].getCurrentPos().getY();
            listPos.add(previous[x][y]);
        }

        return listPos;
    }

    private static int getDistanceFromTile(TilePos pos) {
        return 1;
    }

}
