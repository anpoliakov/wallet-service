package by.anpoliakov;

import by.anpoliakov.services.PreparerTablesAndSchemesDataBase;
import by.anpoliakov.services.in.ConsoleHandler;

/**
 * @author anpoliakov
 * Входная точка приложения
 * */
public class Runner {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в iService!");

        PreparerTablesAndSchemesDataBase.prepare();
        new ConsoleHandler().showMainMenu();

        System.out.println("Приходи ещё!");

    }
}