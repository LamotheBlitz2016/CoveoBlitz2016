package com.coveo.blitz.client.bot;

import com.coveo.blitz.client.dto.GameState;
import lamothe.TilePos;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BoardParser
{
    private static final Logger logger = LogManager.getLogger(BoardParser.class);

    public TilePos[][] parse(String unparsedTiles, int length)
    {
        List<Tile> parsedTiles = new ArrayList<>();
        if (unparsedTiles == null) {
            return new TilePos[0][0];
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

        TilePos[][] tiles = new TilePos[length][length];

        logger.log(Level.INFO, String.format("Got %d tiles. Lenght is %d", parsedTiles.size(), length));

        for(int j = 0; j< length; j++){
            for(int k = 0; k < length; k++) {
                logger.log(Level.INFO, String.format("Accessing tile %d on %d", j * length + k, parsedTiles.size()));
                tiles[j][k] = new TilePos(parsedTiles.get(j * length + k), new GameState.Position(j, k));
            }
        }
        return tiles;
    }

}
