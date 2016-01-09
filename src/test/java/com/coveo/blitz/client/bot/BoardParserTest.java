package com.coveo.blitz.client.bot;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class BoardParserTest
{
    BoardParser boardParser = new BoardParser();

    @Test
    public void nullValueReturnsEmptyList()
    {
        assertEquals(0, boardParser.parse(null).size());
    }

    @Test
    public void emptyValueReturnsEmptyList()
    {
        assertEquals(0, boardParser.parse("").size());
    }

    @Test
    public void singleSymbolReturnsSingleTile()
    {
        assertEquals(Arrays.asList(Tile.Wall), boardParser.parse("##"));
    }

    @Test
    public void oddStringLengthReturnsFullPartialList()
    {
        assertEquals(Arrays.asList(Tile.Wall), boardParser.parse("##X"));
    }

    @Test
    public void longStringReturnsLotsOfTiles()
    {
        assertEquals(Arrays.asList(Tile.Wall, Tile.Wall, Tile.Air, Tile.Wall, Tile.MinePlayer1, Tile.Wall),
                     boardParser.parse("####  ##$1##"));
    }

    @Test
    public void invalidSymbolReturnsUnknownTile()
    {
        assertEquals(Arrays.asList(Tile.UNKNOWN), boardParser.parse("!!"));
    }

}
