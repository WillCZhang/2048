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

    public static Game getInstance(int[] blocks) {
        if (instance == null)
            instance = new Game(blocks);
        return instance;
    }

    private Game() {
        blocks = new int[TOTEL_NUM];
        emptyBlcoks = new HashSet<Integer>();
        updateEmptyBlock();
        addNewBlock();
        addNewBlock();
    }

    private Game(int[] blocks) {
        this.blocks = blocks;
        emptyBlcoks = new HashSet<Integer>();
        updateEmptyBlock();
    }

    public void move(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            move();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            flip();
            move();
            flip();
        } else if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP) {
            rotateBlocksByDiagonal();
            move();
            rotateBlocksByDiagonal();
        } else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN) {
            rotateBlocksByDiagonal();
            flip();
            move();
            flip();
            rotateBlocksByDiagonal();
        }
    }

    private void move() {
        updateEmptyBlock();
        boolean isMoved = false;
        for (int col = 0; col < BLOCK_PER_COL; col++)
            if (handleRow(col))
                isMoved = true;
        if (isMoved)
            addNewBlock();
    }

    private boolean handleRow(int col) {
        boolean result = false;
        for (int curr = 0; curr < BLOCK_PER_ROW - 1; curr++) {
            if (getNumByPos(curr, col) == 0) {
                int next = findNextNonZero(col, curr);
                if (next != curr) {
                    swap(curr, col, next, col);
                    emptyBlcoks.add(getIndexByPos(next, col));
                    emptyBlcoks.remove(getIndexByPos(curr, col));
                    result = true;
                }
            }
            int next = findNextNonZero(col, curr);
            if (next != curr && getNumByPos(curr, col) == getNumByPos(next, col)) {
                setNumByPos(curr, col, FACTOR * getNumByPos(curr, col));
                setNumByPos(next, col, 0);
                emptyBlcoks.add(getIndexByPos(next, col));
                result = true;
            }
        }
        return result;
    }

    private int findNextNonZero(int col, int curr) {
        for (int next = curr + 1; next < BLOCK_PER_ROW; next++)
            if (getNumByPos(next, col) != 0)
                return next;
        return curr;
    }

    private void rotateBlocksByDiagonal() {
        for (int col = 0; col < BLOCK_PER_COL; col++)
            for (int row = 0; row <= col; row++)
                swap(row, col, col, row);
    }

    private void flip() {
        for (int col = 0; col < BLOCK_PER_COL; col++)
            for (int row = 0; row < BLOCK_PER_ROW / 2; row++)
                swap(row, col, BLOCK_PER_ROW - 1 - row, col);
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int a = getIndexByPos(x1, y1);
        int b = getIndexByPos(x2, y2);
        int c = blocks[b];
        blocks[b] = blocks[a];
        blocks[a] = c;
        // TODO: WHY exor doesn't work???
//        blocks[a] = blocks[a] ^ blocks[b];
//        blocks[b] = blocks[a] ^ blocks[b];
//        blocks[a] = blocks[a] ^ blocks[b];
    }

    // TODO: prob need a better algorithm
    private void addNewBlock() {
        int random = getRandom(emptyBlcoks.size());
        int emptySpot = 0;
        while (random >= 0) {
            emptySpot = emptyBlcoks.iterator().next();
            random--;
        }
        blocks[emptySpot] = twoOrFour();
        emptyBlcoks.remove(emptySpot);
    }

    private void updateEmptyBlock() {
        emptyBlcoks.clear();
        for (int i = 0; i < TOTEL_NUM; i++)
            if (blocks[i] == 0)
                emptyBlcoks.add(i);
    }

    private int getRandom(int bound) {
        return RANDOM.nextInt(bound);
    }

    private int twoOrFour() {
        return getRandom(blocks.length) >= (blocks.length / 2) ? 2 : 4;
    }

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
