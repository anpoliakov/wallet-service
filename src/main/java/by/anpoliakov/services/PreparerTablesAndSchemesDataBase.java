package by.anpoliakov.services;

import by.anpoliakov.services.constants.Constants;
import by.anpoliakov.services.constants.ConstantsSQL;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Данный класс отвечает за миграцию БД (загружает актуальные версии changesets)
 * и подготавливает схему БД куда будут помещены служебные таблицы Liquibase
 * */
public class PreparerTablesAndSchemesDataBase {

    public static void prepare(){
        createSystemSchemeLiquibase();
        Database database = null;
        
        try {
            Connection connection = ConnectionManager.createConnection();
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(ConstantsSQL.NAME_SYSTEM_SCHEMA_LIQUIBASE);

            Liquibase liquibase = new Liquibase(Constants.PATH_TO_CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                database.close();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

            ConnectionManager.closeConnection();
        }
    }

    private static void createSystemSchemeLiquibase(){
        Connection connection = ConnectionManager.createConnection();
        try (Statement st = connection.createStatement()){
            st.execute("CREATE SCHEMA IF NOT EXISTS " + ConstantsSQL.NAME_SYSTEM_SCHEMA_LIQUIBASE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.closeConnection();
        }
    }

}
