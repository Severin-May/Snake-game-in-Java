package model;

import java.util.LinkedList;
import java.util.Random;

public class GameLevel {
    public Position apple;
    public final int sizeGrid = 30;
    public final LevelItem[][] level;
    public final LinkedList<Position> snake;
    public boolean gameOver;
    public int numOfApples;
    
    public GameLevel(Direction defaultDir) {
        
        level = new LevelItem[sizeGrid][sizeGrid];
        snake = new LinkedList<>();
        snake.add(new Position(sizeGrid/2,sizeGrid/2));
        Position tail = snake.getFirst().translate(defaultDir);
        while(tail.equals(snake.getFirst().translate(defaultDir))) {
            tail = snake.getFirst().translate(Direction.randomDir());
        }
        
        snake.add(tail);
        gameOver = false;
        
        for(int i = 0; i < sizeGrid; i++) {
           for(int j = 0; j < sizeGrid; j++) {
              level[i][j] = LevelItem.EMPTY;  
           } 
        }
        
        for(int i = 0; i < sizeGrid*0.2; i++) {
            //rocks can be forbidden to be put in center
             Position pos = Position.getRandomPos(sizeGrid);
             if(isFree(pos) && !collidesItself(pos)) {                
                level[pos.x][pos.y] = LevelItem.ROCK;  
             }
             else {
                 pos = Position.getRandomPos(sizeGrid);
                 level[pos.x][pos.y] = LevelItem.ROCK; 
             }
        }
        putApple();
      
    }
    
    public boolean isGameEnded() {
        return gameOver;
    }
    
    public GameLevel(GameLevel gl) {
        level = new LevelItem[sizeGrid][sizeGrid];
        apple = new Position(gl.apple.x, gl.apple.y);
        snake = (LinkedList<Position>)gl.snake.clone();
        for (int i = 0; i < sizeGrid; i++){
            System.arraycopy(gl.level[i], 0, level[i], 0, sizeGrid);
        }
    }
      
    public boolean isValidPosition(Position p){
        return (p.x >= 0 && p.y >= 0 && p.x < sizeGrid && p.y < sizeGrid);
    }
    
    public boolean isFree(Position p){
        if (!isValidPosition(p)) return false;
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.EMPTY);
    }
    
    public void putApple() {
        Position pos = Position.getRandomPos(sizeGrid);
        while(!isFree(pos) || collidesItself(pos)) { //added collidesItself
            pos = Position.getRandomPos(sizeGrid);
        }
        apple = pos; 
    }
    
    public boolean isApple(Position p) {
        if(p.equals(apple)) return true;
            return false;
    }
        
    public boolean moveSnake(Direction d){
        Position curr = snake.getFirst();
        Position next = curr.translate(d);
        
        if (isFree(next) && !collidesItself(next) ) { //&& !collidesItself(next)
            snake.addFirst(next);
            if(isApple(next)) {
                numOfApples++;
                putApple();           
            }
            else {
                snake.removeLast(); 
            }

            return true;   
        } 
        gameOver = true;
        return false;
    }
    
    public boolean collidesItself(Position next) {
        //System.out.println("collides");
        return snake.contains(next);
    }
    
//    public void printLevel(){
//        
//        Position curr = snake.getFirst();
//        for (int i = 0; i < sizeGrid; i++){
//            for (int j = 0; j < sizeGrid; j++){
//                if (i == y && j == x)
//                    System.out.print('@');
//                else 
//                    System.out.print(level[i][j].representation);
//            }
//            System.out.println("");
//        }
//    }
    
}
