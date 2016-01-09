package com.coveo.blitz.client.bot;

import java.util.ArrayList;
import java.util.List;

public class BoardParser
{
    public List<Tile> parse(String unparsedTiles)
    {
        List<Tile> parsedTiles = new ArrayList<>();
        if (unparsedTiles == null) {
            return parsedTiles;
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

        return parsedTiles;
    }

}
