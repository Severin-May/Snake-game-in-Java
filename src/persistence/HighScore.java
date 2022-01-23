package persistence;

import java.util.Objects;
import model.GameID;

public class HighScore {
    public final String name;
    public final int level;
    public final int numApples;
    
    public HighScore(String name, int level, int numApples){
        this.name = name;
        this.level = level;
        this.numApples = numApples;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.numApples);
        hash = 89 * hash + this.level;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HighScore other = (HighScore) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.numApples, other.numApples)) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
        return  name + " " + level + ": " + numApples;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getNumApples() {
        return numApples;
    }
    
    
}


