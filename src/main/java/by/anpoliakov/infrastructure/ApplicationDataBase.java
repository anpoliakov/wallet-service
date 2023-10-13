package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domainServices.PlayerRepository;
import by.anpoliakov.domainServices.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** База данных приложения на паттерне singleton, хранит информацию о пользователях в playerMap,
 * так же хранит информацию о всех транзакциях в transactions */
public  class ApplicationDataBase implements PlayerRepository, TransactionRepository {
    private static ApplicationDataBase instance;

    /** key - уникальный ID транзакции, value - сама транзакция */
    private Map<String, Transaction> transactions;

    /** key - логин, value - сам объект  */
    private Map<String, Player> playerMap;

    private ApplicationDataBase() {
        playerMap = new HashMap<>(6);
        transactions = new HashMap<>(10);
    }

    /** метод получения объекта - он же доступ к БД */
    public static synchronized ApplicationDataBase getInstance() {
        if (instance == null) {
            instance = new ApplicationDataBase();
        }
        return instance;
    }

    @Override
    public void add(Player player) {
        String key = player.getLogin();
        playerMap.put(key, player);
    }

    @Override
    public boolean existPlayerByLogin(String login) {
        return playerMap.containsKey(login);
    }

    @Override
    public Player getPlayerByLoginAndPassword(String login, String password) {
        Player player = playerMap.get(login);

        if (player != null && player.getPassword().equals(password)) {
            return player;
        } else {
            return null;
        }
    }


    @Override
    public List<Transaction> getTransactionsByListID(List<String> listID) {
        List<Transaction> listTransactions = new ArrayList<>();

        for(String id : listID){
            if(transactions.containsKey(id)){
                listTransactions.add(transactions.get(id));
            }
        }

        return listTransactions;
    }

    @Override
    public void addTransaction(String idTransaction, Transaction transaction) {
        transactions.put(idTransaction, transaction);
    }

    @Override
    public boolean checkUniqueTransactionById(String idTransaction) {
        return transactions.containsKey(idTransaction);
    }
}
