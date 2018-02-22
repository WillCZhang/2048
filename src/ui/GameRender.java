package ui;

import game.Game;

import java.awt.*;

public class GameRender {
    public static final String FONT_NAME = "Arial";
    public static final int TILES_MARGIN = 8;
    public static final int BLOCK_SIZE = GameApp.WIDTH / Game.BLOCK_PER_ROW - 2 * TILES_MARGIN;
    public static final int HEIGHT_BASE = GameApp.HEIGHT - GameApp.WIDTH;
    private Game game;
    private GameApp gameApp;

    public GameRender(Game game, GameApp gameApp) {
        this.game = game;
        this.gameApp = gameApp;
    }

    public void draw(Graphics graphics) {
        Graphics2D g = ((Graphics2D) graphics);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        for (int col = 0; col < Game.BLOCK_PER_COL; col++)
            for (int row = 0; row < Game.BLOCK_PER_ROW; row++)
                drawBlock(g, row, col);
        drawWinOrLose(g);
    }

    private void drawBlock(Graphics g, int x, int y) {
        int value = game.getNumByPos(x, y);
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y) + HEIGHT_BASE;
        g.setColor(getBackground(value));
        g.fillRoundRect(xOffset, yOffset, BLOCK_SIZE, BLOCK_SIZE, 14, 14);
        g.setColor(getForeground(value));
        final int frontSize = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, frontSize);
        g.setFont(font);

        String s = String.valueOf(value);
        final FontMetrics fm = gameApp.getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value != 0)
            g.drawString(s, xOffset + (BLOCK_SIZE - w) / 2, yOffset + BLOCK_SIZE - (BLOCK_SIZE - h) / 2 - 2);
    }

    private void drawWinOrLose(Graphics2D g) {
        boolean isWin = game.isWin();
        boolean isLose = game.isGameOver();
        if (isLose)
            drawLose(g);
        else if (isWin)
            drawWin(g);
    }

    private void drawWin(Graphics2D g) {
        prep(g);
        g.drawString("You won!", 68, 150);
        after(g);
        g.drawString("Press R to play again, or C to continue", 80, gameApp.getHeight() - 40);
    }

    private void drawLose(Graphics2D g) {
        prep(g);
        g.drawString("Game over :(", 50, 155);
        after(g);
        g.drawString("Press R to play again", 80, 180);
    }

    private void prep(Graphics2D g) {
        g.setColor(new Color(255, 255, 255, 30));
        g.fillRect(0, 0, gameApp.getWidth(), gameApp.getHeight());
        g.setColor(new Color(78, 139, 202));
        g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
    }

    private void after(Graphics2D g) {
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 22));
        g.setColor(new Color(128, 128, 128, 128));
    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + BLOCK_SIZE + TILES_MARGIN) + TILES_MARGIN;
    }

    private Color getBackground(int value) {
        return Colour.getBackColor(value);
    }

    public Color getForeground(int value) {
        return Colour.getFrontColor(value);
    }

    private static class Colour {
        private static Color normal = new Color(0xcdc1b4);
        private static Color C2 = new Color(0xeee4da);
        private static Color C4 = new Color(0xede0c8);
        private static Color C8 = new Color(0xf2b179);
        private static Color C16 = new Color(0xf59563);
        private static Color C32 = new Color(0xf67c5f);
        private static Color C64 = new Color(0xf65e3b);
        private static Color C128 = new Color(0xEEE061);
        private static Color C256 = new Color(0xF0CF5E);
        private static Color C512 = new Color(0xF1D37D);
        private static Color C1024 = new Color(0xEDC965);
        private static Color C2048 = new Color(0xDEAE6A);
        private static Color C4096 = new Color(0xED7A14);
        private static Color BRIGHT = new Color(0xf9f6f2);
        private static Color DARK = new Color(0x776e65);

        private static Color getBackColor(int value) {
            switch (value) {
                case 2:    return C2;
                case 4:    return C4;
                case 8:    return C8;
                case 16:   return C16;
                case 32:   return C32;
                case 64:   return C64;
                case 128:  return C128;
                case 256:  return C256;
                case 512:  return C512;
                case 1024: return C1024;
                case 2048: return C2048;
                case 4096: return C4096;
                default: return normal;
            }
        }

        private static Color getFrontColor(int value) {
            return value < 16 ? DARK : BRIGHT;
        }
    }
}
