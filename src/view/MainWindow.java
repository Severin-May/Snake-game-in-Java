package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import model.Direction;
import model.Game;
import model.GameID;

public class MainWindow extends JFrame {
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel; 
    private Timer newFrameTimer;
    private JLabel timeLabel;
    private long startTime;
    private Timer timer;
    private int speed = 3;

    
    public MainWindow() throws IOException{
        game = new Game();
        
        setTitle("Snake");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("res/head.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuGameLevel = new JMenu("Levels");
        JMenu menuGameScale = new JMenu("Scale");
        createGameLevelMenuItems(menuGameLevel);
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);
           
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("HighScoresTable") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(game.getHighScores(), MainWindow.this);
            }
        });
        
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JMenuItem menuRestart = new JMenuItem(new AbstractAction("Restart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        menuGame.add(menuGameLevel);
        menuGame.add(menuGameScale);
        menuGame.add(menuHighScores);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        menuGame.add(menuRestart);

        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");

        add(gameStatLabel, BorderLayout.NORTH);
        try { add(board = new Board(game), BorderLayout.CENTER); } catch (IOException ex) {}
        
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                if (!game.isLevelLoaded()) return;
                int kk = ke.getKeyCode();
                Direction d = null;
                switch (kk){
                    case KeyEvent.VK_LEFT:  d = Direction.LEFT; break;
                    case KeyEvent.VK_RIGHT: d = Direction.RIGHT; break;
                    case KeyEvent.VK_UP:    d = Direction.UP; break;
                    case KeyEvent.VK_DOWN:  d = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: game.loadGame();
                    
                }
                refreshGameStatLabel();
                if(d != null) {
                    game.changeDir(d);
                }
                board.repaint();
                
//                if (d != null && game.step(d)){
//                    if (game.isGameEnded()){
//                        String msg = "Congrats!";
//                        if (game.isBetterHighScore()){
//                            msg += " Baby boy!";
//                        }
//                        JOptionPane.showMessageDialog(MainWindow.this, msg, "Játék vége", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                } 
            }
        });

        timeLabel = new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        setTimer();
        
        getContentPane().add(getTimeLabel(), BorderLayout.SOUTH);
        board.refresh();
        levelSpeed();
//        newFrameTimer = new Timer(1000 / speed, new NewFrameListener());
//        newFrameTimer.start();
        
        setResizable(false);
        setLocationRelativeTo(null);
        game.loadGame();
        board.setScale(0.5); // board.refresh();
        pack();
        refreshGameStatLabel();
        setVisible(true);
    }

    
    private void refreshGameStatLabel(){
        String s = "Eaten apples: " + game.getNumOfApples();
        gameStatLabel.setText(s);
    }
    
    private void levelSpeed() {
        if(newFrameTimer != null) {
            newFrameTimer.stop();
        }
        newFrameTimer = new Timer(1000 / speed, new NewFrameListener());
        newFrameTimer.start();
        
    }
    
    private void createGameLevelMenuItems(JMenu menu){
        
        Integer[] levels = {1,2,3};
        for (Integer i : levels){
            JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    speed = i; //System.out.println(speed);
                    levelSpeed();
                    restart();
                }
            });
            menu.add(item);
        }
        
    }
    
    private void createScaleMenuItems(JMenu menu, double from, double to, double by){
        while (from <= to){
            final double scale = from;
            JMenuItem item = new JMenuItem(new AbstractAction(from + "x") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) pack();
                }
            });
            menu.add(item);
            
            if (from == to) break;
            from += by;
            if (from > to) from = to;
        }
    }
    
    public void setTimer() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(elapsedTime() + " ms");
            }
        });
        startTime = System.currentTimeMillis();
        timer.start();
    }
    
    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {}
    }  

    public void restart() {
        game.loadGame();
        board.refresh();
        pack();
        refreshGameStatLabel();
        setTimer();
        //levelSpeed();
    }
    
    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
    
    public JLabel getTimeLabel() {
        return timeLabel;
    }
    
    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(!game.isGameEnded()) game.step();
            board.repaint();
            
            if(game.isGameEnded()) {
                String name = (String)JOptionPane.showInputDialog(MainWindow.this, "Game Over!\nPlease, write your name: ", "Player's name", JOptionPane.QUESTION_MESSAGE);
                if(name != null) game.saveTheScore(name, speed);
                restart();
            }
        }

    }
}

