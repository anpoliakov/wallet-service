package by.anpoliakov.domain;

import java.util.ArrayList;
import java.util.List;

/** Сущность представляющая из себя игрока: его баланс, логин и пароль */
public class Player {
    private Double balance;
    private String login; // Уникальный индификатор
    private String password;

    /** Упорядоченный список ID транзакций пользователя - в порядке выполнения */
    private final List<String> listIDTransactions;

    // При начальной инициализации объекта - он пуст: нет денег, нет операций
    public Player(String login, String password) {
        balance = 0.0;
        this.login = login;
        this.password = password;
        listIDTransactions = new ArrayList<>();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getListIDTransactions() {
        return listIDTransactions;
    }

    public void addTransactionID(String idTransaction) {
        listIDTransactions.add(idTransaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!balance.equals(player.balance)) return false;
        if (!login.equals(player.login)) return false;
        return password.equals(player.password);
    }

    @Override
    public int hashCode() {
        int result = balance.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
