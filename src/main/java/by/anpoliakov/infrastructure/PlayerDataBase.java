package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domainServices.PlayerRepository;
import by.anpoliakov.services.ConnectionManager;
import by.anpoliakov.services.constants.SQLConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Класс для работы с сущеностью Player в БД */
public  class PlayerDataBase implements PlayerRepository{
    @Override
    public void addPlayer(Player player) {
        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement (SQLConstants.CREATE_PLAYER);
            //при создании нового игрока в БД для безопасности можно не передавать значение баланса - а устанавливать по default
            pst.setDouble(1, player.getBalance());
            pst.setString(2, player.getLogin());
            //пароль желательно зашифровать хотя бы в md5 формате (не хранить голый в БД)
            pst.setString(3, player.getPassword());

            pst.executeUpdate();
            connection.commit();
            System.out.println("Транзакция успешно завершена.");
        } catch (SQLException e) {
            rollBack(connection, e);
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }
    }

    /** Метод сообщает существует ли такой пользователь в БД
     * @Param login - логин пользователя
     * @return - true если пользователь существует в БД, false если нет
     * */
    @Override
    public boolean existPlayerByLogin(String login) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            connection = ConnectionManager.createConnection();

            pst = connection.prepareStatement (SQLConstants.HAS_PLAYER);
            pst.setString(1, login);
            rs = pst.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return result;
    }

    @Override
    public boolean updateBalancePlayer(int player_id, Double newBalance) {
        Connection connection = null;
        PreparedStatement pst = null;
        boolean updated = false;

        try {
            connection = ConnectionManager.createConnection();
            connection.setAutoCommit(false);

            pst = connection.prepareStatement(SQLConstants.UPDATE_PLAYER_BALANCE);
            pst.setDouble(1, newBalance);
            pst.setInt(2, player_id);

            if(pst.executeUpdate() > 0){
                updated = true;
            }

            connection.commit();
            System.out.println("Транзакция успешно завершена.");
        } catch (SQLException e) {
            rollBack(connection, e);
        } finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return updated;
    }

    /** Метод получения объекта пользователя по логину и паролю
     * @Param login - логин с пользователя с формы login
     * @Param password - пароль с пользователя с формы login
     * @return - объект пользователя или null если нет такого пользователя
     * */
    @Override
    public Player getPlayerByLoginAndPassword(String login, String password) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Player player = null;

        try {
            connection = ConnectionManager.createConnection();

            pst = connection.prepareStatement(SQLConstants.SELECT_PLAYER);
            pst.setString(1, login);
            pst.setString(2, password);
            rs = pst.executeQuery();

            while (rs.next()){
                int player_id = rs.getInt(SQLConstants.PLAYER_ID_LABEL);
                double tempBalance = rs.getDouble(SQLConstants.BALANCE_LABEL);
                player = new Player(player_id, tempBalance, login, password);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return player;
    }

    @Override
    public Double getBalancePlayer(int player_id) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Double balance = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(SQLConstants.SELECT_BALANCE);
            pst.setInt(1, player_id);
            rs = pst.executeQuery();

            while (rs.next()){
                balance = rs.getDouble(SQLConstants.BALANCE_LABEL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return balance;
    }

    private void rollBack(Connection connection, SQLException e){
        System.err.println("Произошла ошибка: " + e.getMessage());
        if (connection != null) {
            try {
                connection.rollback();
                System.err.println("Транзакция отменена.");
            } catch (SQLException rollbackException) {
                System.err.println("Ошибка при откате транзакции: " + rollbackException.getMessage());
            }
        }
    }
}
