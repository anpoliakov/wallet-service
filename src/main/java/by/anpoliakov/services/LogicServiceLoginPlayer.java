package by.anpoliakov.services;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import by.anpoliakov.infrastructure.TransactionDataBase;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LogicServiceLoginPlayer {
    private static TransactionDataBase transactionDataBase = TransactionDataBase.getInstance();

    public static boolean getInputAmountCredit(Player player){
        Scanner scanner = new Scanner(System.in);
        boolean isSuccessful = false;
        String idTransaction;
        Double amount;

        try {
            //принимаем ввод от пользователя
            amount = scanner.nextDouble();
            scanner.nextLine();

            if(amount != 0){
                boolean hasTransactionID = false;

                do{
                    System.out.println("Введите уникальный индификатор транзакции:");
                    idTransaction = scanner.nextLine().trim();

                    if(transactionDataBase.checkUniqueTransactionById(idTransaction)){
                        System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                    }else {
                        player.setBalance(player.getBalance() + amount);
                        Transaction transaction = new Transaction(amount, TypeOperation.CREDIT, new Date(), player);
                        transactionDataBase.addTransaction(idTransaction, transaction);
                        player.addTransactionID(idTransaction);
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


    public static boolean getInputAmountDebit(Player player){
        Scanner scanner = new Scanner(System.in);
        boolean isSuccessful = false;
        String idTransaction;
        Double amount;

        try {
            amount = scanner.nextDouble();
            scanner.nextLine();

            if(amount != 0){
                if(!(player.getBalance() - amount < 0)){
                    boolean hasTransactionID = true;

                    do{
                        System.out.println("Введите уникальный индификатор транзакции:");
                        idTransaction = scanner.nextLine().trim();

                        /**
                         * Выполняем проверку ID транзакции - есть ли такая в БД + проверку не введён ли просто Enter
                         * */
                        if(transactionDataBase.checkUniqueTransactionById(idTransaction) && !idTransaction.isEmpty()){
                            System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                        }else {
                            if(amount != 0){
                                player.setBalance(player.getBalance() - amount);
                                Transaction transaction = new Transaction(amount, TypeOperation.DEBIT, new Date(), player);
                                transactionDataBase.addTransaction(idTransaction,transaction);
                                player.addTransactionID(idTransaction);
                            }
                            hasTransactionID = false;
                        }
                    }while (hasTransactionID);
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
}
