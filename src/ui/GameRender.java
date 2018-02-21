package ui;

import game.Game;

import java.awt.*;

public class GameRender {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int BLOCK_SIZE = 64;
    private static final int TILES_MARGIN = 16;
    private Game game;
    private GameApp gameApp;

    public GameRender(Game game, GameApp gameApp) {
        this.game = game;
        this.gameApp = gameApp;
    }

    public void draw(Graphics graphics) {
        for (int col = 0; col < Game.BLOCK_PER_COL; col++)
            for (int row = 0; row < Game.BLOCK_PER_ROW; row++)
                drawBlock(graphics, row, col);
    }

    private void drawBlock(Graphics graphics, int x, int y) {
        Graphics2D g = ((Graphics2D) graphics);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        int value = game.getNumByPos(x, y);
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
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

//        if (myWin || myLose) {
//            g.setColor(new Color(255, 255, 255, 30));
//            g.fillRect(0, 0, getWidth(), getHeight());
//            g.setColor(new Color(78, 139, 202));
//            g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
//            if (myWin) {
//                g.drawString("You won!", 68, 150);
//            }
//            if (myLose) {
//                g.drawString("Game over!", 50, 130);
//                g.drawString("You lose!", 64, 200);
//            }
//            if (myWin || myLose) {
//                g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
//                g.setColor(new Color(128, 128, 128, 128));
//                g.drawString("Press ESC to play again", 80, getHeight() - 40);
//            }
//        }
//        g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
//        g.drawString("Score: " + myScore, 200, 365);
    }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + BLOCK_SIZE) + TILES_MARGIN;
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
        private static Color C128 = new Color(0xedcf72);
        private static Color C256 = new Color(0xedcc61);
        private static Color C512 = new Color(0xedc850);
        private static Color C1024 = new Color(0xedc53f);
        private static Color C2048 = new Color(0xedc22e);
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
