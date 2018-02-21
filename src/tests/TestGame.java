package tests;

import game.Game;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;

public class TestGame {
    private Game game1;
    private Game game2;

    @Before
    public void setUp() throws Exception {
        int[] blocks1 = {2,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        game1 = Game.getInstance(blocks1);

        int[] blocks2 = {4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        game2 = Game.getInstance(blocks2);
    }

    @Test
    public void game1MoveTest() {
        game1.move(KeyEvent.VK_RIGHT);
        assertEquals(2, game1.getNumByPos(2,0));
        assertEquals(4, game1.getNumByPos(3,0));
    }

    @Test
    public void game2MoveTest() {
        game2.move(KeyEvent.VK_RIGHT);
        assertEquals(0, game2.getNumByPos(2,0));
        assertEquals(8, game2.getNumByPos(3,0));
    }
}
