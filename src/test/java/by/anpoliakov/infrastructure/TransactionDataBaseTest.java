package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionDataBaseTest {
    private static TransactionDataBase transactionDataBase;
    private static Transaction transaction;
    private static Player player;
    private static String idTransaction;

    @BeforeClass
    public static void setUp(){
        transactionDataBase = TransactionDataBase.getInstance();
        idTransaction = "idtransaction123";
        player = new Player("TestLogin", "TestPassword");
        transaction = new Transaction(100.12, TypeOperation.CREDIT, new Date(), player);
    }

    @Test
    public void transactionShouldBeAdded() {
        transactionDataBase.addTransaction(idTransaction, transaction);
        assertTrue(transactionDataBase.checkUniqueTransactionById(idTransaction));
    }

    @Test
    public void instanceShouldBeExist() {
        assertNotNull(transactionDataBase);
    }

    @Test
    public void getTransactionsPlayer() {
        List<Transaction> transactionsPlayer = transactionDataBase.getTransactionsPlayer(player);
        assertEquals(1, transactionsPlayer.size());
        assertEquals(transaction, transactionsPlayer.get(0));
    }

    @Test
    public void checkUniqueTransactionByIdShouldBeWork() {
        assertFalse(transactionDataBase.checkUniqueTransactionById(idTransaction));
    }
}