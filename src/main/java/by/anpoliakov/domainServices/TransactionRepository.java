package by.anpoliakov.domainServices;

import by.anpoliakov.domain.Transaction;

import java.util.List;

/** интерфейс с описанием действий над сущностью
 * @see Transaction */
public interface TransactionRepository {
    List <Transaction> getTransactionsByListID(List<String> listID);
    void addTransaction (String idTransaction, Transaction transaction);
    boolean checkUniqueTransactionById(String id);
}
