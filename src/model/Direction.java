package model;

import java.util.Random;

public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), UP(0, -1), RIGHT(1, 0);
    
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public static Direction getOpposite(Direction d) {
        if(d == DOWN) return UP;
        else if(d == LEFT) return RIGHT;
        else if(d == RIGHT) return LEFT;
        else return DOWN;
    }
    
    public static Direction randomDir() {
        Random random = new Random();
        return values()[random.nextInt(4)];
    }
    
    public final int x, y;
}
