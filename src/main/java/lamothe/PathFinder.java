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
public class PathFinder {
    private BoardTile[][] previous = null;
    private BoardTile[][] map;
    private static int[][] dist;


    public PathFinder(BoardTile[][] map) {
        this.map = map;
        this.previous = new BoardTile[map.length][map.length];
    }

    public void calculate(GameState.Position startingPoint) {
        this.previous = shortedPath(this.map, new BoardTile(map[startingPoint.getX()][startingPoint.getY()].getCurrentTile(), startingPoint));
    }

    private BoardTile[][] shortedPath(BoardTile[][] map, BoardTile startPoint) {
        this.previous = new BoardTile[map.length][map.length];
        this.dist = new int[map.length][map.length];
        LinkedList<BoardTile> Q = new LinkedList<>();
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                this.dist[x][y] = Integer.MAX_VALUE;
                this.previous[x][y] = null;
                Q.add(map[x][y]);
            }
        }

        this.dist[startPoint.getCurrentPos().getX()][startPoint.getCurrentPos().getY()] = 0;
        while(!Q.isEmpty()) {
            BoardTile u = Q.stream().sorted((p1, p2) -> Integer.compare(dist[p1.getCurrentPos().getX()][p1.getCurrentPos().getY()],
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

    public List<BoardTile> getBestPath(BoardTile startPoint, BoardTile endPoint) {
        int x = endPoint.getCurrentPos().getX();
        int y = endPoint.getCurrentPos().getY();
        List<BoardTile> listPos = new LinkedList<>();

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

    public  Optional<BoardTile> getNextPosForBestMine(BoardTile startPoint, Integer playerNumber) {
        List<BoardTile> mines = new LinkedList<>();

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                if (isWantedMine(map[x][y].getCurrentTile().getSymbol(), playerNumber))
                    mines.add(map[x][y]);
            }
        }

        Optional<BoardTile> bestMine = mines.stream().sorted((m1, m2) -> Integer.compare(dist[m1.getCurrentPos().getX()][m1.getCurrentPos().getY()], dist[m2.getCurrentPos().getX()][m2.getCurrentPos().getY()])).findFirst();
        return bestMine.isPresent() ? getBestPath(startPoint, bestMine.get()).stream().findFirst() : bestMine;

    }

    public BoardTile getNextPosForHeroAttack(BoardTile startPoint, GameState.Hero hero){
        return getBestPath(startPoint, this.map[hero.getPos().getX()][hero.getPos().getY()]).stream().findFirst().get();
    }

    public Optional<BoardTile> getNextPosForBestBeer(BoardTile startPoint) {
        List<BoardTile> mines = new LinkedList<>();

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map.length; y++) {
                if (isBeer(map[x][y].getCurrentTile().getSymbol()))
                    mines.add(map[x][y]);
            }
        }

        BoardTile bestMine = mines.stream().sorted((m1, m2) -> Integer.compare(dist[m1.getCurrentPos().getX()][m1.getCurrentPos().getY()], dist[m2.getCurrentPos().getX()][m2.getCurrentPos().getY()])).findFirst().get();

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

    private int getDistanceFromTile(BoardTile pos) {
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

    public static BotMove findDirection(BoardTile pos, BoardTile dest){

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
