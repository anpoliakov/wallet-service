package by.anpoliakov.services;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import by.anpoliakov.infrastructure.PlayerDataBase;
import by.anpoliakov.infrastructure.TransactionDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LogicServiceLoginPlayer {
    private TransactionDataBase transactionDataBase = null;
    private PlayerDataBase playerDataBase = null;

    public LogicServiceLoginPlayer() {
        this.transactionDataBase = new TransactionDataBase();
        this.playerDataBase = new PlayerDataBase();
    }

    public boolean getInputAmountCredit(Player player){
        Scanner scanner = new Scanner(System.in);
        boolean isSuccessful = false;
        String transaction_uid;
        Double amount;

        try {
            //принимаем ввод от пользователя
            amount = scanner.nextDouble();
            scanner.nextLine();

            if(amount != 0){
                boolean hasTransactionID = false;

                do{
                    System.out.println("Введите уникальный индификатор транзакции:");
                    transaction_uid = scanner.nextLine().trim();

                    if(transactionDataBase.checkUniqueTransactionById(transaction_uid)){
                        System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                    }else {
                        Double new_balance = player.getBalance() + amount;
                        if(playerDataBase.updateBalancePlayer(player.getPlayer_id(), new_balance)){
                            System.out.println("Успешно обнавленён - " + player.getLogin());
                        }
                        Transaction transaction = new Transaction(transaction_uid, amount, TypeOperation.CREDIT, player);
                        transactionDataBase.addTransaction(transaction);
                        hasTransactionID = true;
                    }
                }while (!hasTransactionID);
            }

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - запятая. Попробуйте ещё");
            isSuccessful = getInputAmountCredit(player);
        }

        return isSuccessful;
    }


    public boolean getInputAmountDebit(Player player){
        Scanner scanner = new Scanner(System.in);
        boolean isSuccessful = false;
        String transaction_uid;
        Double amount;

        try {
            amount = scanner.nextDouble();
            scanner.nextLine();

            if(amount != 0){
                if(!(player.getBalance() - amount < 0)){
                    boolean has_transaction_uid = true;

                    do{
                        System.out.println("Введите уникальный индификатор транзакции:");
                        transaction_uid = scanner.nextLine().trim();

                        /**
                         * Выполняем проверку ID транзакции - есть ли такая в БД + проверку не введён ли просто Enter
                         * */
                        if(transactionDataBase.checkUniqueTransactionById(transaction_uid) && !transaction_uid.isEmpty()){
                            System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                        }else {
                            if(amount != 0){
                                playerDataBase.updateBalancePlayer(player.getPlayer_id(), player.getBalance() - amount);
                                Transaction transaction = new Transaction(transaction_uid, amount, TypeOperation.DEBIT, player);
                                transactionDataBase.addTransaction(transaction);
                            }
                            has_transaction_uid = false;
                        }
                    }while (has_transaction_uid);
                }else {
                    System.out.println("ОШИБКА: Недостаточно денег для снятия. Ваш остаток на счёте = " + player.getBalance() + ", попробуйте ещё раз!");
                    getInputAmountDebit(player);
                }
            }

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - запятая. Попробуйте ещё");
            isSuccessful = getInputAmountDebit(player);
        }

        return isSuccessful;
    }

    public void showHistoryTransaction(Player player){
        List<Transaction> transactions = transactionDataBase.getTransactionsPlayer(player);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        for (Transaction transaction : transactions){
            System.out.println("Сумма транзакции: " + transaction.getAmount()
                    + " бел.руб, тип транзакции: " + transaction.getTypeOperation()
                    + ", время: " + dateFormat.format(transaction.getDate()));
        }
    }
}
