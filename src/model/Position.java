package model;

import java.util.Random;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }    
    
    public Position translate(Direction d){
        return new Position(x + d.x, y + d.y);
    }
    
    public static Position getRandomPos(int size) {
        Random rand = new Random();
        return new Position(rand.nextInt(size), rand.nextInt(size));
    }
    
    @Override
    public boolean equals(Object o) {
    if( o == this) return true;
    if( o == null) return false;
    if(!(o instanceof Position)) return false;

    Position other = (Position) o;
        return Integer.compare(this.x, other.x) == 0 && Integer.compare(this.y, other.y) == 0;
    }
}
