package by.anpoliakov.services.in;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import by.anpoliakov.infrastructure.ApplicationDataBase;

import java.text.SimpleDateFormat;
import java.util.*;

/** Класс для работы с Авторизированным пользователем, предоставляет все возможности
 * работы с сервисом */
public class LoginConsoleHandlerForPlayer {
    ApplicationDataBase applicationDataBase = null;
    Player player = null;

    public LoginConsoleHandlerForPlayer(Player player) {
        this.applicationDataBase = ApplicationDataBase.getInstance();
        this.player = player;

        if(player != null){
            showMainLoginMenu();
        }
    }

    private void showMainLoginMenu() {
        int choice = -1;

        do{
            System.out.println("################ Меню игрока |" + player.getLogin() + "| ################");
            System.out.println("Текущий баланс = " + player.getBalanse() + " бел.руб");

            System.out.println("[1] Пополнение");
            System.out.println("[2] Снятие");
            System.out.println("[3] История");
            System.out.println("[0] Выход");
            System.out.print(">>> Ожидание ввода:");

            switch (choice = getPlayerInput()){
                case 1 -> {credit();}
                case 2 -> {debit();}
                case 3 -> {getTransactionHistory();}
                case 0 -> {}
                default -> System.out.println("Некоректный ввод пункта меню, попробуйте ещё раз!");
            }
        } while (choice != 0);
    }

    private void credit() {
        Scanner scanner = new Scanner(System.in);
        String idTransaction = null;
        double amount = 0;

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Введите сумму пополнения: ");
        try {
            amount = scanner.nextDouble();
            boolean temp = true;
            scanner.nextLine();
            if(amount == 0) return;

            do{
                System.out.println("Введите уникальный индификатор транзакции:");
                idTransaction = scanner.nextLine().trim();

                if(applicationDataBase.checkUniqueTransactionById(idTransaction)){
                    System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                }else {
                    if(amount != 0){
                        player.setBalanse(player.getBalanse() + amount);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        Transaction transaction = new Transaction(amount, TypeOperation.CREDIT, dateFormat.format(new Date()), player);
                        applicationDataBase.addTransaction(idTransaction,transaction);
                        player.addTransactionID(idTransaction);
                    }
                    temp = false;
                }
            }while (temp);

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - запятая. Попробуйте ещё");
            credit();
        }
    }

    private void debit() {
        Scanner scanner = new Scanner(System.in);
        String idTransaction = null;
        double amount = 0;

        System.out.println("-----------------------------------");
        System.out.println("Введите сумму которую желаете снять: ");
        try {
            amount = scanner.nextDouble();
            boolean temp = true;
            scanner.nextLine();
            if(amount == 0) return;

            if(!(player.getBalanse() - amount < 0)){
                do{
                    System.out.println("Введите уникальный индификатор транзакции:");
                    idTransaction = scanner.nextLine().trim();

                    /**
                     * Выполняем проверку ID транзакции - есть ли такая в БД + проверку не введён ли просто Enter
                     * */
                    if(applicationDataBase.checkUniqueTransactionById(idTransaction) && !idTransaction.isEmpty()){
                        System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                    }else {
                        if(amount != 0){
                            player.setBalanse(player.getBalanse() - amount);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                            Transaction transaction = new Transaction(amount, TypeOperation.DEBIT, dateFormat.format(new Date()), player);
                            applicationDataBase.addTransaction(idTransaction,transaction);
                            player.addTransactionID(idTransaction);
                        }
                        temp = false;
                    }
                }while (temp);
            }else {
                System.out.println("ОШИБКА: Ваш остаток на основном счёте = " + player.getBalanse() + ", попробуйте ещё раз!");
                debit();
            }

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - запятая. Попробуйте ещё");
            credit();
        }
    }

    private void getTransactionHistory() {
        System.out.println("---ИСТОРИЯ----ИСТОРИЯ-----ИСТОРИЯ----|" + player.getLogin() + "|---ИСТОРИЯ----ИСТОРИЯ-----ИСТОРИЯ----");

        List<Transaction> transactions = applicationDataBase.getTransactionsByListID(player.getListTransactions());
        for (Transaction transaction : transactions){
            System.out.println("Сумма транзакции: " + transaction.getAmount() + "бел. руб, тип транзакции: " + transaction.getType() +
                    ", время: " + transaction.getDate());
        }

        System.out.println("\nНажмите Enter что бы продолжить.....");
        new Scanner(System.in).nextLine();
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

        return choice;
    }
}
