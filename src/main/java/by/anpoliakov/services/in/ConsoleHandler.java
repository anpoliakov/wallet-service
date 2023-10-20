package by.anpoliakov.services.in;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domainServices.PlayerRepository;
import by.anpoliakov.infrastructure.PlayerDataBase;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для работы с пользовательским вводом в консоль
 * Основное меню, меню регистрации и авторизации - когда пользователь ещё не в системе! */
public class ConsoleHandler {
    private PlayerRepository playerRepository = null;

    public ConsoleHandler() {
        playerRepository = new PlayerDataBase();
    }

    /** Основное меню программы */
    public void showMainMenu() {
        int choice = -1;

        do{
            System.out.println("################ ГЛАВНОЕ МЕНЮ ################");
            System.out.println("[1] Авторизация");
            System.out.println("[2] Регистрация");
            System.out.println("[0] Выход");
            System.out.print(">>> Ожидание ввода:");

            switch (choice = getPlayerInput()){
                case 1 -> {showLoginMenu();}
                case 2 -> {showRegistrationMenu();}
                case 0 -> {}
                default -> System.out.println("Некоректный ввод пункта меню, попробуйте ещё раз!");
            }
        } while (choice != 0);
    }

    /** Меню с авторизацией пользователя */
    private void showLoginMenu() {
        System.out.println("################# АВТОРИЗАЦИЯ #################");
        System.out.println("[для возвращения в главное меню, введите 0]");
        Scanner scanner = new Scanner(System.in);

            System.out.println("Введите ваш логин: ");
            String login = scanner.nextLine().trim();
            if(login.equals("0")){
                return;
            }

            System.out.println("Введите ваш пароль: ");
            String password = scanner.nextLine().trim();
            if(password.equals("0")){
                return;
            }

        Player player = playerRepository.getPlayerByLoginAndPassword(login, password);
        if(player != null){
            new ConsoleHandlerForLoginPlayer(player);
        }else {
            System.out.println("Данный пользователь не найден! Попробуйте ещё раз!");
            showLoginMenu();
        }
    }

    /** Меню с регитсрацией пользователя */
    private void showRegistrationMenu() {
        System.out.println("################# РЕГИСТРАЦИЯ #################");
        System.out.println("[для возвращения в главное меню, введите 0]");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ваш логин: ");
        String login = scanner.nextLine().trim();
        if(login.equals("0")){
            return;
        }

        System.out.println("Введите ваш пароль: ");
        String password = scanner.nextLine().trim();
        if(password.equals("0")){
            return;
        }

        if(!playerRepository.existPlayerByLogin(login)){
            playerRepository.addPlayer(new Player(login, password));
            System.out.println("Вы успешно зарегистрированы! Можете войти под своей учётной записью.");
        }else {
            System.out.println("Пользователь " + login + " уже существует! Попробуйте ещё раз");
            showRegistrationMenu();
        }
    }

    /**
     * Метод предназначенный для обработки пользовательского ввода номера меню
     * @return числовое значение (номер выбранного меню)
     * @return OR -1 при любом другом случае
     * */
    private int getPlayerInput(){
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch (NoSuchElementException e){
            choice = -1;
        }

        // Scanner не закрывал специально, есть ошибка с которой нет времени разбираться
        // В реалях данной задачи - не критично
        return choice;
    }
}
