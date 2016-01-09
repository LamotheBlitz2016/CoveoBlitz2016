package com.coveo.blitz.client.bot;

import com.coveo.blitz.client.dto.GameState;
import lamothe.BoardTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BoardParser
{
    private static final Logger logger = LogManager.getLogger(BoardParser.class);

    public BoardTile[][] parse(String unparsedTiles, int length)
    {
        List<Tile> parsedTiles = new ArrayList<>();
        if (unparsedTiles == null) {
            return new BoardTile[0][0];
        }
        int i = 0;
        while (i + 2 <= unparsedTiles.length()) {
            String tileToParse = unparsedTiles.substring(i, i + 2);
            Tile parsedTile = null;
            if (tileToParse.length() == 2) {
                for (Tile tile : Tile.values()) {
                    if (tile.getSymbol().equals(tileToParse)) {
                        parsedTile = tile;
                    }
                }
                parsedTiles.add(parsedTile == null ? Tile.UNKNOWN : parsedTile);
            }
            i = i + 2;
        }

        BoardTile[][] tiles = new BoardTile[length][length];

        for(int j = 0; j< length; j++){
            for(int k = 0; k < length; k++) {
                tiles[j][k] = new BoardTile(parsedTiles.get(j * length + k), new GameState.Position(j, k));
            }
        }
        return tiles;
    }

}
