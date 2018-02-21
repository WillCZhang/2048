package tests;

import game.Game;

import java.awt.event.KeyEvent;

public class AlterTest {
    public static void main(String[] args) {
        int[] blocks = {2,4,0,4,0,2,0,0,2,0,0,0,0,0,4,0};

//        Test1
//        Game game1 = Game.getInstance(blocks);
//        game1.move(KeyEvent.VK_RIGHT);
//        int[] blocks1_expect = {0,0,2,8,0,0,0,2,0,0,0,2,0,0,0,4};
//        checkDiff(game1, blocks1_expect);

//        Test2
        Game game2 = Game.getInstance(blocks);
        game2.move(KeyEvent.VK_UP);
        int[] blocks2_expect = {4,4,4,4,0,2,0,0,0,0,0,0,0,0,0,0};
        checkDiff(game2, blocks2_expect);
    }

    private static void checkDiff(Game game, int[] blockExpect) {
        int diff = 0;
        for (int i = 0; i < Game.BLOCK_PER_COL; i++)
            for (int j = 0; j < Game.BLOCK_PER_ROW; j++)
                if (game.getNumByPos(i, j) != blockExpect[game.getIndexByPos(i, j)])
                    diff++;
        if (diff > 1)
            System.out.println("Failed");
    }
}
