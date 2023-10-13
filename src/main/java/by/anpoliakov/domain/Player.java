package by.anpoliakov.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //лучше бы спользовать для денег класс BigDecimal (но это тестовое задание)
    private Double balanse;

    /** Уникальный индификатор в тестовом проекте - login */
    private String login;
    private String password;

    /** Упорядоченный список ID транзакций пользователя - в порядке выполнения */
    private List<String> listTransactions;

    public Player(String login, String password) {
        balanse = 0.0;
        this.login = login;
        this.password = password;
        listTransactions = new ArrayList<>();
    }

    public Double getBalanse() {
        return balanse;
    }

    public void setBalanse(Double balanse) {
        this.balanse = balanse;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getListTransactions() {
        return listTransactions;
    }

    public void addTransactionID(String idTransaction) {
        listTransactions.add(idTransaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!balanse.equals(player.balanse)) return false;
        if (!login.equals(player.login)) return false;
        return password.equals(player.password);
    }

    @Override
    public int hashCode() {
        int result = balanse.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
