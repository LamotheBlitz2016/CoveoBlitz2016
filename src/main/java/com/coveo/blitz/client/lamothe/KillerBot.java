package com.coveo.blitz.client.lamothe;

import com.coveo.blitz.client.bot.BoardParser;
import com.coveo.blitz.client.bot.BotMove;
import com.coveo.blitz.client.bot.SimpleBot;
import com.coveo.blitz.client.bot.Tile;
import com.coveo.blitz.client.dto.GameState;
import com.coveo.blitz.client.dto.Move;

import java.util.List;

/**
 * Created by olivier on 2016-01-09.
 */
public class KillerBot implements SimpleBot {

    @Override
    public BotMove move(GameState gameState) {
        List<Tile> tiles = new BoardParser().parse(gameState.getGame().getBoard().getTiles());
        final int BOARD_LENGHT =  gameState.getGame().getBoard().getSize();
        Tile[][] aTiles = new Tile[BOARD_LENGHT][BOARD_LENGHT];

        return null;
    }

    @Override
    public void setup() {

    }

    @Override
    public void shutdown() {

    }

    private Move getBestMoveTowardsPosition(GameState.Position position){
        return null;
    }
}
