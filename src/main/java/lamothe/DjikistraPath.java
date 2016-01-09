package lamothe;

import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by jeremiep on 2016-01-09.
 */
public class DjikistraPath {
    private TilePos[][] previous = null;
    private TilePos[][] map;
    private static int[][] dist;


    public DjikistraPath(TilePos[][] map) {
        this.map = map;
        this.previous = new TilePos[map.length][map.length];
    }

    public void calculate(GameState.Position startingPoint) {
        this.previous = shortedPath(this.map, new TilePos(map[startingPoint.getX()][startingPoint.getY()].getCurrentTile(), startingPoint));
    }

    private TilePos[][] shortedPath(TilePos[][] map, TilePos startPoint) {
        this.previous = new TilePos[map.length][map.length];
        this.dist = new int[map.length][map.length];
        LinkedList<TilePos> Q = new LinkedList<>();
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                this.dist[x][y] = Integer.MAX_VALUE;
                this.previous[x][y] = null;
                Q.add(map[x][y]);
            }
        }

        this.dist[startPoint.getCurrentPos().getX()][startPoint.getCurrentPos().getY()] = 0;
        while(!Q.isEmpty()) {
            TilePos u = Q.stream().sorted((p1, p2) -> Integer.compare(dist[p1.getCurrentPos().getX()][p1.getCurrentPos().getY()],
                    dist[p2.getCurrentPos().getX()][p2.getCurrentPos().getY()])).findFirst().get();
            Q.remove(u);

            LinkedList<GameState.Position> neighbours = new LinkedList<>();
            neighbours.add(new GameState.Position(u.getCurrentPos().getX() + 1, u.getCurrentPos().getY()));
            neighbours.add(new GameState.Position(u.getCurrentPos().getX() - 1, u.getCurrentPos().getY()));
            neighbours.add(new GameState.Position(u.getCurrentPos().getX(), u.getCurrentPos().getY() + 1));
            neighbours.add(new GameState.Position(u.getCurrentPos().getX(), u.getCurrentPos().getY() - 1));
            for(GameState.Position n : neighbours) {
                if(n.getX() >= 0 && n.getX() < map.length && n.getY() >= 0 && n.getY() < map.length) {
                    int alt = this.dist[u.getCurrentPos().getX()][u.getCurrentPos().getY()] + getDistanceFromTile(map[n.getX()][n.getY()]);
                    if(alt < this.dist[n.getX()][n.getY()]) {
                        this.dist[n.getX()][n.getY()] = alt;
                        this.previous[n.getX()][n.getY()] = u;
                    }
                }
            }
        }

        return this.previous;
    }

    public List<TilePos> getBestPath(TilePos startPoint, TilePos endPoint) {
        int x = endPoint.getCurrentPos().getX();
        int y = endPoint.getCurrentPos().getY();
        List<TilePos> listPos = new LinkedList<>();

        while (this.previous[x][y] != null){
            int newX = previous[x][y].getCurrentPos().getX();
            int newY = previous[x][y].getCurrentPos().getY();
            listPos.add(previous[x][y]);
            x = newX;
            y = newY;
        }

        Collections.reverse(listPos);
        listPos.add(endPoint);
        listPos.remove(0);
        return listPos;
    }

    public  Optional<TilePos> getNextPosForBestMine(TilePos startPoint, Integer playerNumber) {
        List<TilePos> mines = new LinkedList<>();

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                if (isWantedMine(map[x][y].getCurrentTile().getSymbol(), playerNumber))
                    mines.add(map[x][y]);
            }
        }

        Optional<TilePos> bestMine = mines.stream().sorted((m1, m2) -> Integer.compare(dist[m1.getCurrentPos().getX()][m1.getCurrentPos().getY()], dist[m2.getCurrentPos().getX()][m2.getCurrentPos().getY()])).findFirst();
        return bestMine.isPresent() ? getBestPath(startPoint, bestMine.get()).stream().findFirst() : bestMine;

    }

    public TilePos getNextPosForHeroAttack(TilePos startPoint, GameState.Hero hero){
        return getBestPath(startPoint, this.map[hero.getPos().getX()][hero.getPos().getY()]).stream().findFirst().get();
    }

    public Optional<TilePos> getNextPosForBestBeer(TilePos startPoint) {
        List<TilePos> mines = new LinkedList<>();

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                if (isBeer(map[x][y].getCurrentTile().getSymbol()))
                    mines.add(map[x][y]);
            }
        }

        TilePos bestMine = mines.stream().sorted((m1, m2) -> Integer.compare(dist[m1.getCurrentPos().getX()][m1.getCurrentPos().getY()], dist[m2.getCurrentPos().getX()][m2.getCurrentPos().getY()])).findFirst().get();

        return getBestPath(startPoint, bestMine).stream().findFirst();

    }

    private static boolean isWantedMine(String symbol, Integer playerNumber){
        if (symbol.startsWith("$")){
            return !symbol.contains(playerNumber.toString());
        } else {
            return false;
        }
    }

    private static boolean isBeer(String symbol) {
        return Tile.Tavern.getSymbol().equalsIgnoreCase(symbol);
    }

    private int getDistanceFromTile(TilePos pos) {
        if(pos.getCurrentTile() == Tile.Wall) {
            return 100;
        } else if(pos.getCurrentTile().getSymbol().startsWith("$")) {
            return 100;
        } else if(pos.getCurrentTile() == Tile.Spikes) {
            return 5;
        } else if(pos.getCurrentTile() == Tile.Tavern) {
            return 100;
        }

        return 1;
    }

    public static BotMove findDirection(TilePos pos, TilePos dest){

        if(pos.getCurrentPos().getX() > dest.getCurrentPos().getX()){
            return BotMove.NORTH;
        }

        if(pos.getCurrentPos().getX() < dest.getCurrentPos().getX()){
            return BotMove.SOUTH;
        }

        if(pos.getCurrentPos().getY() > dest.getCurrentPos().getY()){
            return BotMove.WEST;
        }

        if(pos.getCurrentPos().getY() < dest.getCurrentPos().getY()){
            return BotMove.EAST;
        }

        return BotMove.STAY;
    }

}
