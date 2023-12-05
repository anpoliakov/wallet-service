package by.anpoliakov.domainServices;

import by.anpoliakov.domain.Player;

import java.math.BigDecimal;

/** интерфейс с описанием действий над сущностью
 * @see Player */
public interface PlayerRepository {
    void addPlayer(Player player);
    boolean existPlayerByLogin(String login);
    boolean updateBalancePlayer(int player_id, BigDecimal newBalance);
    Player getPlayerByLoginAndPassword(String login, String password);
    BigDecimal getBalancePlayer(int player_id);
}
