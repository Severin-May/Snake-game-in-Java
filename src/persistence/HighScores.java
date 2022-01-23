/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author bli
 */
public class HighScores {

    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public HighScores() throws SQLException {
        String dbURL = "jdbc:derby://localhost:1527/highscores;";
        connection = DriverManager.getConnection(dbURL);
        String insertQuery = "INSERT INTO HIGHSCORES (NAME, level, numApples) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE NAME=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int level = results.getInt("LEVEL");
            int numApples = results.getInt("numApples");
            highScores.add(new HighScore(name, level, numApples));
        }
        sortHighScores(highScores);
        return highScores;
    }
  
    public boolean isRecordExist(String name, ArrayList<HighScore> highscores) {
        boolean isThere = false;
        for(HighScore h: highscores) {
            if(h.getName().equals(name))
                isThere = true;
        }
        
        return isThere;
    }

    public void putHighScore(String name, int level, int score) throws SQLException {
        insertScore(name, level, score);
    }

    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getNumApples() - t.getNumApples();
            }
        });
    }
    
    private int getScore(String name) throws SQLException {
        String query = "SELECT NUMAPPLES FROM HIGHSCORES WHERE NAME='" + name + "'";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        result.next();
        
        return result.getInt(1);
    }

    private void insertScore(String name, int level, int numApples) throws SQLException {
        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setString(1, name);
        insertStatement.setInt(2, level);
        insertStatement.setInt(3, numApples);
        insertStatement.executeUpdate();
    }

//    private void deleteScores(String name) throws SQLException {
//        deleteStatement.setString(1, name);
//        deleteStatement.executeUpdate();
//    }
}
