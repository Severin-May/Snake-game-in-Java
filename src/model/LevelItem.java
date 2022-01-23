package model;

public enum LevelItem {
    ROCK('#'), EMPTY('0'), APPLE('*');
    LevelItem(char rep){ representation = rep; }
    public final char representation;
}
