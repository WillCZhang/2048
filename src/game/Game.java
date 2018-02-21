package game;

import java.awt.event.KeyEvent;
import java.util.*;

public class Game {
    public static final int FACTOR = 2;
    public static final int BLOCK_PER_ROW = 4;
    public static final int BLOCK_PER_COL = BLOCK_PER_ROW;
    public static final int TOTEL_NUM = BLOCK_PER_COL * BLOCK_PER_ROW;
    public static final int MAX_NUM = 4096;
    public static final Random RANDOM = new Random();
    private static Game instance;
    private int[] blocks;
    private Set<Integer> emptyBlcoks;

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    // TODO: read stored game
    public static Game getInstance(String record) {
        return null;
    }

    private Game() {
        blocks = new int[TOTEL_NUM];
        emptyBlcoks = new HashSet<Integer>();
        setupGame();
    }

    private void setupGame() {
        for (int i = 0; i < blocks.length; i ++)
            emptyBlcoks.add(i);
        addNewBlock();
        addNewBlock();
    }

    public void move(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            move();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            rotateBlock180();
            move();
            rotateBlock180();
        } else if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP) {
            rotateBlocksByDiagonal();
            move();
            rotateBlocksByDiagonal();
        } else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN) {
            rotateBlock180();
            rotateBlocksByDiagonal();
            move();
            rotateBlocksByDiagonal();
            rotateBlock180();
        }
        if (hasEmptySpot())
            addNewBlock();
    }

    private void move() {
        for (int col = 0; col < BLOCK_PER_COL; col++)
            handleRow(col);
    }

    private void handleRow(int col) {
        for (int curr = 0; curr < BLOCK_PER_ROW - 1; curr++) {
            if (getNumByPos(curr, col) == 0) {
                int next = findNextNonZero(col, curr);
                swap(curr, col, next, col);
                emptyBlcoks.add(getIndexByPos(next, col)); // TODO: BUG!
                emptyBlcoks.remove(getIndexByPos(curr, col));
            }
            int next = findNextNonZero(col, curr);
            if (next != curr && getNumByPos(curr, col) == getNumByPos(next, col)) {
                setNumByPos(curr, col, FACTOR * getNumByPos(curr, col));
                setNumByPos(next, col, 0);
                emptyBlcoks.add(getIndexByPos(next, col));
            }
        }
    }

    private int findNextNonZero(int col, int curr) {
        for (int next = curr + 1; next < BLOCK_PER_ROW; next++)
            if (getNumByPos(next, col) != 0)
                return getIndexByPos(next, col);
        return curr;
    }

    /**
     * This method requires @code BLOCK_PER_ROW and @code BLOCK_PER_COL to be the power of 2
     */
    private void rotateBlock180() {
        for (int col = 0; col < BLOCK_PER_COL; col++)
            for (int row = 0; row < BLOCK_PER_ROW / 2; row++)
                swap(row, col, BLOCK_PER_COL - 1 - row, BLOCK_PER_ROW - 1 - col);
    }

    private void rotateBlocksByDiagonal() {
        for (int col = 0; col < BLOCK_PER_COL; col++)
            for (int row = 0; row <= col; row++)
                swap(row, col, col, row);
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int a = getIndexByPos(x1, y1);
        int b = getIndexByPos(x2, y2);
        blocks[a] = blocks[a] ^ blocks[b];
        blocks[b] = blocks[a] ^ blocks[b];
        blocks[a] = blocks[a] ^ blocks[b];
    }

    private void addNewBlock() {
        int random = getRandom(emptyBlcoks.size());
        int emptySpot = 0;
        while (random >= 0) {
            emptySpot = emptyBlcoks.iterator().next();
            random--;
        }
        assert blocks[emptySpot] == 0;
        blocks[emptySpot] = twoOrFour();
        emptyBlcoks.remove(emptySpot);
    }

    private boolean hasEmptySpot() {
        return emptyBlcoks.size() != 0;
    }

    private int getRandom(int bound) {
        return RANDOM.nextInt(bound);
    }

    private int twoOrFour() {
        return getRandom(blocks.length) >= (blocks.length / 2) ? 2 : 4;
    }

    /**
     * Returns the number at position (x, y), 0-index as defult
     * @param x
     * @param y
     * @return the number at position (x, y)
     */
    public int getNumByPos(int x, int y) {
        return getNumByPos(x, y, 0);
    }

    public int getNumByPos(int x, int y, int index) {
        return blocks[getIndexByPos(x, y, index)];
    }

    private void setNumByPos(int x, int y, int number) {
        blocks[getIndexByPos(x, y)] = number;
    }

    public int getIndexByPos(int x, int y) {
        return getIndexByPos(x, y, 0);
    }

    public int getIndexByPos(int x, int y, int index) {
        return (y - index) * BLOCK_PER_ROW + x - index;
    }

    public boolean isGameOver() {
        if (emptyBlcoks.size() > 0)
            return false;
        for (int col = 0; col < BLOCK_PER_COL; col++)
            for (int row = 0; row < BLOCK_PER_ROW - 1; row++)
                if (getNumByPos(row, col) == getNumByPos(row + 1, col))
                    return false;
        for (int row = 0; row < BLOCK_PER_COL; row++)
            for (int col = 0; col < BLOCK_PER_ROW * (BLOCK_PER_COL - 1); col += BLOCK_PER_ROW)
                if (getNumByPos(row, col) == getNumByPos(row, col += BLOCK_PER_ROW))
                    return false;
        return true;
    }
}
