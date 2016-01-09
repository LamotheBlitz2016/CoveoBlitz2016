package com.coveo.blitz.client.bot;

public enum Tile
{
    Air("  "),
    Wall("##"),
    Spikes("^^"),
    Tavern("[]"),
    Hero1("@1"),
    Hero2("@2"),
    Hero3("@3"),
    Hero4("@4"),
    MineNeutral("$-"),
    MinePlayer1("$1"),
    MinePlayer2("$2"),
    MinePlayer3("$3"),
    MinePlayer4("$4"),
    UNKNOWN("??");

    private final String symbol;

    Tile(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public String toString()
    {
        return symbol;
    }

    public String getSymbol()
    {
        return symbol;
    }
}
