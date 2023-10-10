package by.anpoliakov.domainServices;

import by.anpoliakov.domain.Player;

/** интерфейс с описанием действий над сущностью
 * @see Player */
public interface PlayerRepository {
    void add(Player player);
    boolean existPlayerByLogin(String login);
    Player getPlayerByLoginAndPassword(String login, String password);
}
