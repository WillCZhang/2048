package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameApp extends JFrame {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    private static final Color GAME_COLOUR = new Color(255, 238, 168);
    private Game game;
    private ScoreRender scoreRender;
    private GameRender gameRender;

    public GameApp() {
        super("2048");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = Game.getInstance();
        scoreRender = new ScoreRender(game, this);
        gameRender = new GameRender(game, this);
        addKeyListener(new KeyHandler());
        centreOnScreen();
        setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(GAME_COLOUR);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        scoreRender.draw(graphics);
        gameRender.draw(graphics);
    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                game.reset();
                update(getGraphics());
            } else if (e.getKeyCode() == KeyEvent.VK_X)
                System.exit(0);
            if (game.move(e.getKeyCode()))
                update(getGraphics());
        }
    }

    public static void main(String[] args) {
        new GameApp();
    }
}
