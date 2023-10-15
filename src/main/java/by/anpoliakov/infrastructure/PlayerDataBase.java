package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domainServices.PlayerRepository;

import java.util.HashMap;
import java.util.Map;

/** БД на паттерне singleton для хранения сущности Player */
public  class PlayerDataBase implements PlayerRepository{
    private static PlayerDataBase instance;

    /** где: KEY - логин, VALUE - сам объект  */
    private Map<String, Player> players;

    private PlayerDataBase() {
        players = new HashMap<>(5);
    }

    /** метод получения экземпляра БД */
    public static synchronized PlayerDataBase getInstance() {
        if (instance == null) {
            instance = new PlayerDataBase();
        }
        return instance;
    }

    /** Метод добавления пользователя в БД
     * @Param player - объект пользователя
     * */
    @Override
    public void add(Player player) {
        String key = player.getLogin();
        players.put(key, player);
    }

    /** Метод сообщает существует ли такой пользователь в БД
     * @Param login - логин пользователя
     * @return - true если пользователь существует в БД, false если нет
     * */
    @Override
    public boolean existPlayerByLogin(String login) {
        return players.containsKey(login);
    }

    /** Метод получения объекта пользователя по логину и паролю
     * @Param login - логин с пользователя с формы login
     * @Param password - пароль с пользователя с формы login
     * @return - объект пользователя или null если нет такого пользователя
     * */
    @Override
    public Player getPlayerByLoginAndPassword(String login, String password) {
        Player player = players.get(login);

        if (player != null && player.getPassword().equals(password)) {
            return player;
        } else {
            return null;
        }
    }
}
