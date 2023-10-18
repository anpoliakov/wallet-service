package by.anpoliakov.services;

import by.anpoliakov.services.constants.Constants;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class LiquibaseRunner {

    public static void start(){
        try {
            Connection connection = ConnectionManager.createConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(Constants.PATH_TO_CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionManager.closeConnection();
        }
    }

}
