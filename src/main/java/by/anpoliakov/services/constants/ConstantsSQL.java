package by.anpoliakov.services.constants;

public class ConstantsSQL {
    public static final String CREATE_PLAYER = "INSERT INTO entities.players (balance, login, password) " +
            "VALUES (?, ?, ?)";
    public static final String HAS_PLAYER = "SELECT * FROM entities.players WHERE login = ?";
    public static final String SELECT_PLAYER = "SELECT * FROM entities.players WHERE login = ? AND password = ?";
    public static final String SELECT_ALL_TRANSACTIONS_BY_PLAYER = "SELECT * FROM entities.transactions INNER JOIN entities.operations ON " +
            "entities.transactions.operation_id = entities.operations.operation_id WHERE entities.transactions.player_id = ?";

    public static final String BALANCE_LABEL = "balance";
    public static final String LOGIN_LABEL = "login";
    public static final String PASSWORD_LABEL = "password";
    public static final String PLAYER_ID_LABEL = "player_id";
    public static final String TRANSACTION_ID_LABEL = "transaction_id";
    public static final String TRANSACTION_UID_LABEL = "transaction_uid";
    public static final String AMOUNT_LABEL = "amount";
    public static final String DATE = "date";
    public static final String NAME_OPERATION_LABEL = "name_operation";
    public static final String IS_UNIQUE_TRANSACTION_UID = "SELECT * FROM entities.transactions WHERE transaction_uid = ?";
    public static final String INSERT_TRANSACTION = "INSERT INTO entities.transactions " +
            "(transaction_uid, amount, operation_id, date, player_id) VALUES (?,?,?,?,?)";
    public static final String UPDATE_PLAYER_BALANCE = "UPDATE entities.players SET balance = ? WHERE player_id = ?";
    public static final String SELECT_BALANCE = "SELECT * FROM entities.players WHERE player_id = ?";


    public static final String NAME_SYSTEM_SCHEMA_LIQUIBASE = "system_tables_liquibase";
}
