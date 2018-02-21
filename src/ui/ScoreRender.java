package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;

import static ui.GameRender.FONT_NAME;

public class ScoreRender extends JPanel{
    public static final int HEIGHT_LIMITATION = GameRender.HEIGHT_BASE;
    public static final int HEIGHT_BASE = HEIGHT_LIMITATION / 8;
    public static final Color WELCOME_COLOR = new Color(0x776e65);
    public static final String WELCOME = "2048";

    private Game game;
    private GameApp gameApp;

    public ScoreRender(Game game, GameApp gameApp) {
        this.game = game;
        this.gameApp = gameApp;
    }

    public void draw(Graphics graphics) {
        setupWelcome(graphics);
    }

    private void setupWelcome(Graphics graphics) {
        final int frontSize = 40;
        final Font font = new Font(FONT_NAME, Font.PLAIN, frontSize);
        graphics.setFont(font);
        final FontMetrics fm = gameApp.getFontMetrics(font);
        final int w = fm.stringWidth(WELCOME);
        final int h = -(int) fm.getLineMetrics(WELCOME, graphics).getBaselineOffsets()[2];
        int welcomeX = GameApp.WIDTH / 2 - w / 2;
        int welcomeY = HEIGHT_BASE;

        setupWelcomeBackground(graphics, welcomeX, welcomeY, w, h);

        graphics.setColor(WELCOME_COLOR);
        graphics.drawString(WELCOME, welcomeX, welcomeY + h - 5);
    }

    private void setupWelcomeBackground(Graphics graphics, int stringX, int stringY, int w, int h) {
        int wInGap = w / 6;
        int hInGap = h / 6;
        int wOutGap = wInGap / 2;
        int hOutGap = hInGap / 2;
        int xOut = stringX - wInGap - wOutGap;
        int yOut = stringY - hInGap - hOutGap;
        int xIn = stringX - wInGap;
        int yIn = stringY - hInGap;
        Color in = new Color(0xede0c8);
        Color out = new Color(0xf2b179);
        graphics.setColor(out);
        graphics.fillRoundRect(xOut, yOut,
                2 * wOutGap + 2 * wInGap + w, 2 * hOutGap + 2 * hInGap + h, 10, 10);
        graphics.setColor(in);
        graphics.fillRoundRect(xIn, yIn,
                2 * wInGap + w, 2 * hInGap + h, 10, 10);

        drawScore(graphics, yOut + 2 * hOutGap + 2 * hInGap + h);
    }

    private void drawScore(Graphics g, int y) {
        Font savedF = g.getFont();
        Font f = new Font(FONT_NAME, Font.PLAIN, 18);
        g.setFont(f);
        String score = "Score: " + game.getScore();
        int newY = centreString(g, y + 5, f, score);

        String instruction = "Press X to exit | Press R to replay";
        centreString(g, newY + 10, f, instruction);

        g.setFont(savedF);
    }

    private int centreString(Graphics g, int y, Font f, String string) {
        final FontMetrics fm = gameApp.getFontMetrics(f);
        final int w = fm.stringWidth(string);
        final int h = -(int) fm.getLineMetrics(string, g).getBaselineOffsets()[2];
        g.setColor(WELCOME_COLOR);
        g.drawString(string, GameApp.WIDTH / 2 - w / 2, y + h);
        return y + h;
    }
}
