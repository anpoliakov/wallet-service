package by.anpoliakov.services;

import by.anpoliakov.services.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Класс предоставляет Connection к базе данных
 * так же занимается закрытием: Connection, ResultSet, Statement
 * */
public class ConnectionManager {
    private static Connection conn = null;

    //сейчас это можно не делать, но на всякий случай:)
    /** Загружает драйвер баз данных */
    static {
        try {
            Class.forName(Constants.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionManager() {}

    public static Connection createConnection (){
        try {
            if(conn == null || conn.isClosed()){
                Properties properties = loadPropertiesDataBase();
                conn = DriverManager.getConnection(
                        properties.getProperty("URL"),
                        properties.getProperty("USER_NAME"),
                        properties.getProperty("PASSWORD"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            System.err.println("Проверьте наличие необходимых ключей в файле database.properties");
        }

        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Properties loadPropertiesDataBase(){
        Properties properties = null;

        try (FileInputStream fis = new FileInputStream(Constants.PATH_TO_PROPERTIES_DB)) {
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}

