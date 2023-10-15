package by.anpoliakov.services.in;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import by.anpoliakov.infrastructure.TransactionDataBase;

import java.text.SimpleDateFormat;
import java.util.*;

import static by.anpoliakov.services.LogicServiceLoginPlayer.getInputAmountCredit;
import static by.anpoliakov.services.LogicServiceLoginPlayer.getInputAmountDebit;

/** Класс для работы с вводом <u>aвторизированного</u> пользователя */
public class ConsoleHandlerForLoginPlayer {
    private final TransactionDataBase transactionDataBase;
    private final Player player;

    public ConsoleHandlerForLoginPlayer(Player player) throws NullPointerException{
        this.transactionDataBase = TransactionDataBase.getInstance();
        this.player = player;

        if(player != null){
            showMainLoginMenu();
        }else {
            throw new NullPointerException("Был передан Player = null в конструктор класса ConsoleHandlerForLoginPlayer");
        }
    }

    /** Метод показа главного меню авторизированного пользователя */
    private void showMainLoginMenu() {
        int choice = -1;

        do{
            System.out.println("################ Меню игрока |" + player.getLogin() + "| ################");
            System.out.println("Текущий баланс = " + player.getBalance() + " бел.руб");

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

    /** Метод показа меню для пополнения баланса */
    private void credit() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Введите сумму пополнения: ");

        if(getInputAmountCredit(player)){
            System.out.println("-УСПЕХ ОПЕРАЦИИ ПОПОЛНЕНИЯ-");
        }
    }

    /** Метод показа меню для снятия денег */
    private void debit() {
        System.out.println("-----------------------------------");
        System.out.println("Введите сумму которую желаете снять: ");
        if(getInputAmountDebit(player)){
            System.out.println("-УСПЕХ ОПЕРАЦИИ СНЯТИЯ-");
        }

    }

    /** Метод для показа всех транзакций текущего Player */
    private void getTransactionHistory() {
        System.out.println("---ИСТОРИЯ----ИСТОРИЯ-----ИСТОРИЯ----| ИГРОКА: " + player.getLogin() + "|---ИСТОРИЯ----ИСТОРИЯ-----ИСТОРИЯ----");
        List<Transaction> transactions = transactionDataBase.getTransactionsPlayer(player);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        for (Transaction transaction : transactions){
            System.out.println("Сумма транзакции: " + transaction.getAmount()
                    + "бел.руб, тип транзакции: " + transaction.getType()
                    + ", время: " + dateFormat.format(transaction.getDate()));
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
