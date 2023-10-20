package by.anpoliakov.domain;

/** Сущность представляющая из себя игрока: его баланс, логин и пароль */
public class Player {
    private int player_id;
    private Double balance;
    private String login; // Уникальный индификатор
    private String password;

    // При начальной инициализации объекта - он пуст: нет денег, нет операций
    public Player(String login, String password) {
        balance = 0.0;
        this.login = login;
        this.password = password;
    }

    public Player(int player_id, double balance, String login, String password) {
        this.player_id = player_id;
        this.balance = balance;
        this.login = login;
        this.password = password;
    }



    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
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
