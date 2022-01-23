package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import persistence.Database;
import persistence.HighScore;
import res.ResourceLoader;

public class Game {

    private GameLevel gameLevel = null;
    private final Database database;
    private boolean isBetterHighScore = false;
    private Direction defaultDir; //Direction.UP;

    public Game() {
        database = new Database();
        defaultDir = Direction.randomDir();
    }

    public void loadGame(){
        defaultDir = Direction.randomDir();
        gameLevel = new GameLevel(defaultDir);
        isBetterHighScore = false;
    }
    
   // public void printGameLevel(){ gameLevel.printLevel(); }
    
    public boolean step(){
        boolean stepped = (gameLevel.moveSnake(defaultDir));
        if (stepped && isGameEnded()){
           // isBetterHighScore = database.storeHighScore(id, steps);
        }
        return stepped;
    }
    
    public boolean isLevelLoaded(){ return gameLevel != null; }
    public int getLevelRows(){ return gameLevel.sizeGrid; }
    public int getNumOfApples(){ return gameLevel.numOfApples; }
    public LevelItem getItem(int row, int col){ return gameLevel.level[row][col]; }
    public boolean isGameEnded(){ return (gameLevel != null && gameLevel.isGameEnded()); }
    //public boolean isBetterHighScore(){ return isBetterHighScore; }

    public LinkedList<Position> getSnakePos(){ // MAKE IT ~IMMUTABLE
        return gameLevel.snake;
    }
    
    public ArrayList<HighScore> getHighScores() {
        return database.getHighScores();
    }
    
    public void saveTheScore(String name, int level) {
        database.storeHighScore(name, level, gameLevel.numOfApples);
    }
    
    public void changeDir(Direction d) {
        if(Direction.getOpposite(d) != defaultDir) {
            defaultDir = d;
        }
    }
    
    public Position getApplePos() {
        return gameLevel.apple;
    }
    
    public Direction getDefaultDir() {
        return defaultDir;
    }

//    private void addNewGameLevel(GameLevel gameLevel){
//        HashMap<Integer, GameLevel> levelsOfDifficulty;
//        if (gameLevels.containsKey(gameLevel.gameID.difficulty)){
//            levelsOfDifficulty = gameLevels.get(gameLevel.gameID.difficulty);
//            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
//        } else {
//            levelsOfDifficulty = new HashMap<>();
//            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
//            gameLevels.put(gameLevel.gameID.difficulty, levelsOfDifficulty);
//        }
//        database.storeHighScore(gameLevel.gameID, 0);
//    }
     
}

//TODO: database functions should be implemented

//
