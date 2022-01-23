package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private HighScores highScores;


    public Database() {
        try { highScores = new HighScores(); }
        catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<HighScore> getHighScores() {
        try {
            List<HighScore> l = highScores.getHighScores();
            return new ArrayList<HighScore>(l.subList(0, l.size() > 10 ? 10 : l.size())); 
        }
        catch (SQLException ex) {
            return null;
        }
    }
    

    public void storeHighScore(String name, int level, int numApples) {
        try { highScores.putHighScore(name, level, numApples); }
        catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
