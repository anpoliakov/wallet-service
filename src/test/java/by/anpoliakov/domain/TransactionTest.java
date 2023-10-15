package by.anpoliakov.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    private Transaction transaction;
    private Double amount = 105.4;

    @Before
    public void setUp(){
        transaction = new Transaction(amount, TypeOperation.CREDIT, new Date(), new Player("TestLogin", "TestPassword"));
    }

    @Test
    public void getAmountShouldReturnValue() {
        assertEquals(amount, transaction.getAmount());
    }
}