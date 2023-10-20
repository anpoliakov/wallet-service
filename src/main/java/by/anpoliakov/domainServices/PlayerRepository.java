package by.anpoliakov.domainServices;

import by.anpoliakov.domain.Player;

/** интерфейс с описанием действий над сущностью
 * @see Player */
public interface PlayerRepository {
    void addPlayer(Player player);
    boolean existPlayerByLogin(String login);
    boolean updateBalancePlayer(int player_id, Double newBalance);
    Player getPlayerByLoginAndPassword(String login, String password);
    Double getBalancePlayer(int player_id);
}
