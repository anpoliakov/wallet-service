package by.anpoliakov.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    public Player player = null;

    @Before
    public void setUp() {
        player = new Player("TestLogin","TestPassword");
    }

    @Test
    public void getBalanceInNewPlayerShouldBeReturnAsZero() {
        Double balance = player.getBalance();
        assertEquals(Double.valueOf(0.0), balance);
    }

    @Test
    public void getListIDTransactionsInNewPlayerShouldBeEmpty(){
        List<String> listIDTransactions = player.getListIDTransactions();
        assertTrue(listIDTransactions.isEmpty());
    }

    @Test
    public void addTransactionIDInNewPlayerShouldBeAddToList(){
        player.addTransactionID("123456789");
        List<String> listIDTransactions = player.getListIDTransactions();
        assertEquals(1, listIDTransactions.size());
    }
}