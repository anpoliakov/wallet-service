package by.anpoliakov.infrastructure;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domainServices.TransactionRepository;
import by.anpoliakov.services.ConnectionManager;
import by.anpoliakov.services.constants.Constants;
import by.anpoliakov.services.constants.SQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс по управлению сущностью Transaction в БД
 * */
public class TransactionDataBase implements TransactionRepository {

    /** Метод получения списка транзакций переданного в параметры Player */
    @Override
    public List<Transaction> getTransactionsPlayer(Player player) {
        List<Transaction> listTransactions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(SQLConstants.SELECT_ALL_TRANSACTIONS_BY_PLAYER);
            pst.setInt(1, player.getPlayer_id());

            rs = pst.executeQuery();
            while (rs.next()){
                int transaction_id = rs.getInt(SQLConstants.TRANSACTION_ID_LABEL);
                String transaction_uid = rs.getString(SQLConstants.TRANSACTION_UID_LABEL);
                Double amount = rs.getDouble(SQLConstants.AMOUNT_LABEL);
                String name_operation = rs.getString(SQLConstants.NAME_OPERATION_LABEL);
                Date date = rs.getDate(SQLConstants.DATE);

                listTransactions.add(new Transaction(transaction_id, transaction_uid, amount, name_operation, date, player));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }

        return listTransactions;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(SQLConstants.INSERT_TRANSACTION);

            pst.setString(1, transaction.getTransaction_uid());
            pst.setDouble(2, transaction.getAmount());
            pst.setInt(3, transaction.getTypeOperation().ordinal( ) + Constants.CORRECT_VALUE_ENUM_FOR_DATABASE);
            pst.setDate(4, java.sql.Date.valueOf(transaction.getDate().toString()));
            pst.setInt(5, transaction.getRelationToPlayer().getPlayer_id());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeStatement(pst);
            ConnectionManager.closeConnection();
        }
    }

    /** Проверка уникальности id транзакции */
    @Override
    public boolean checkUniqueTransactionById(String transaction_uid) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            connection = ConnectionManager.createConnection();
            pst = connection.prepareStatement(SQLConstants.IS_UNIQUE_TRANSACTION_UID);
            pst.setString(1, transaction_uid);

            rs = pst.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeResultSet(rs);
            ConnectionManager.closeStatement(pst);
        }
        return result;
    }

}
