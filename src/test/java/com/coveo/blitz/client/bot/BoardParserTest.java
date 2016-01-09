package com.coveo.blitz.client.bot;

import static org.junit.Assert.*;

import java.util.Arrays;

import com.coveo.blitz.client.dto.GameState;
import lamothe.TilePos;
import org.junit.Test;

public class BoardParserTest
{
    BoardParser boardParser = new BoardParser();

    @Test
    public void nullValueReturnsEmptyList()
    {
        assertEquals(0, boardParser.parse(null, 0).length);
    }

    @Test
    public void emptyValueReturnsEmptyList()
    {
        assertEquals(0, boardParser.parse("", 0).length);
    }

    @Test
    public void longStringReturnsLotsOfTiles()
    {
        TilePos[][] tiles = boardParser.parse("####  $1", 2);
        TilePos[][] expected = new TilePos[][]{
                {new TilePos(Tile.Wall, new GameState.Position(0, 0)), new TilePos(Tile.Wall, new GameState.Position(0, 1))},
                {new TilePos(Tile.Air, new GameState.Position(1, 0)), new TilePos(Tile.MinePlayer1, new GameState.Position(1, 1))}};

        for(int i =0; i< 2; i++){
            for(int j=0; j<2; j++) {
                assertEquals(expected[i][j].getCurrentPos().getX(), tiles[i][j].getCurrentPos().getX());
                assertEquals(expected[i][j].getCurrentPos().getY(), tiles[i][j].getCurrentPos().getY());
                assertEquals(expected[i][j].getCurrentTile(), tiles[i][j].getCurrentTile());
            }
        }
    }

    @Test
    public void testParse() throws Exception {

    }
}
