package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domainServices.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** БД на паттерне singleton для хранения сущности Transaction
 *
 * Одна едина база данных всех транзакций
 * для простого анализа уникальности определённой транзакции.
 * */
public class TransactionDataBase implements TransactionRepository {
    private static TransactionDataBase instance;

    /** KEY - уникальный ID транзакции, VALUE - сама транзакция */
    private Map<String, Transaction> transactions;

    private TransactionDataBase() {
        transactions = new HashMap<>(10);
    }

    /** метод получения экземпляра БД */
    public static synchronized TransactionDataBase getInstance() {
        if (instance == null) {
            instance = new TransactionDataBase();
        }
        return instance;
    }

    /** Метод получения списка транзакций переданного в параметры Player */
    @Override
    public List<Transaction> getTransactionsPlayer(Player player) {
        List<String> listIDTransactions = player.getListIDTransactions();
        List<Transaction> listTransactions = new ArrayList<>();

        for(String idTransaction : listIDTransactions){
            if(transactions.containsKey(idTransaction)){
                listTransactions.add(transactions.get(idTransaction));
            }
        }

        return listTransactions;
    }

    @Override
    public void addTransaction(String idTransaction, Transaction transaction) {
        transactions.put(idTransaction, transaction);
    }

    /** Проверка уникальности id транзакции */
    @Override
    public boolean checkUniqueTransactionById(String idTransaction) {
        return transactions.containsKey(idTransaction);
    }

}
