package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerDataBaseTest {
    private PlayerDataBase playerDataBase;

    @Before
    public void setUp(){
        playerDataBase = PlayerDataBase.getInstance();
    }

    @Test
    public void playerShouldBeAdded() {
        Player player = new Player("TestLogin", "TestPassword");
        playerDataBase.add(player);
        assertTrue(playerDataBase.existPlayerByLogin("TestLogin"));
    }

    @Test
    public void methodShouldReturnPlayer() {
        Player player = new Player("TestLogin", "TestPassword");
        playerDataBase.add(player);
        assertEquals(player, playerDataBase.getPlayerByLoginAndPassword("TestLogin", "TestPassword"));
    }
}