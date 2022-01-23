package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JPanel;
import model.Game;
import model.LevelItem;
import model.Position;
import res.ResourceLoader;

public class Board extends JPanel {
    private Game game;
    private final Image  rock, apple, head, body, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;
    
    public Board(Game g) throws IOException{
        game = g;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        rock = ResourceLoader.loadImage("res/rock.png");
        apple = ResourceLoader.loadImage("res/apple.png");
        head = ResourceLoader.loadImage("res/head.png");
        body = ResourceLoader.loadImage("res/body.png");
        empty = ResourceLoader.loadImage("res/empty.png");
    }
    
    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    
    public boolean refresh(){
        if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(game.getLevelRows() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D)g;
        int w = game.getLevelRows();
        int h = game.getLevelRows();
        LinkedList<Position> snakePos = game.getSnakePos();
        Position applePos = game.getApplePos();
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                LevelItem li = game.getItem(y, x);
                switch (li){
                    case ROCK: img = rock; break;
//                    case APPLE: img = apple; break;
                    case EMPTY: img = empty; break;
                }
                if (snakePos.contains(new Position(x,y))) img = body;
                if (snakePos.getFirst().equals(new Position(x,y))) img = head;
                if (applePos.equals(new Position(x,y))) img = apple;
                if (img == null) continue;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
    }
    
}
