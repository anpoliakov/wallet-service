package by.anpoliakov.services;

import by.anpoliakov.domain.Player;
import by.anpoliakov.domain.Transaction;
import by.anpoliakov.domain.TypeOperation;
import by.anpoliakov.infrastructure.PlayerDataBase;
import by.anpoliakov.infrastructure.TransactionDataBase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
        boolean isSuccessful = false;
        Scanner scanner = new Scanner(System.in);

        String transaction_uid;
        BigDecimal compareValue = new BigDecimal(0);
        BigDecimal amount;

        try {
            String input = scanner.nextLine().trim();
            amount = new BigDecimal(input);

            /* если введённое число НЕ меньше и НЕ равно 0, проваливаемся дальше */
            if(!(amount.compareTo(compareValue) <= 0)){
                boolean hasTransactionID = false;

                do{
                    System.out.println("Введите уникальный индификатор транзакции:");
                    transaction_uid = scanner.nextLine().trim();

                    if(transactionDataBase.checkUniqueTransactionById(transaction_uid)){
                        System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН! Попробуйте ещё раз:");
                    }else {
                        BigDecimal currentBalance = player.getBalance();
                        BigDecimal balanceAfterCredit = currentBalance.add(amount);

                        playerDataBase.updateBalancePlayer(player.getPlayer_id(), balanceAfterCredit);
                        transactionDataBase.addTransaction(new Transaction(transaction_uid, amount, TypeOperation.CREDIT, player));
                        hasTransactionID = true;
                    }
                }while (!hasTransactionID);
            }

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - точка. Попробуйте ещё");
            isSuccessful = getInputAmountCredit(player);
        }catch (NumberFormatException e){
            System.out.println("Не верный формат ввода значения debit суммы, попробуйте ещё раз!");
            isSuccessful = getInputAmountDebit(player);
        }

        return isSuccessful;
    }

    /** Метод снятия денег с баланса
     * @param player - игрок, с баланса коготоро производим списание средств
     * @return успех/неудача операции
     * */
    public boolean getInputAmountDebit(Player player){
        boolean isSuccessful = false;
        Scanner scanner = new Scanner(System.in);

        String transaction_uid;
        BigDecimal compareValue = new BigDecimal(0);
        BigDecimal amount;


        try {
            String input = scanner.nextLine().trim();
            amount = new BigDecimal(input);

            /* если введённое число НЕ меньше и НЕ равно 0, проваливаемся дальше */
            if(!(amount.compareTo(compareValue) <= 0)){
                BigDecimal currentBalance = player.getBalance();
                BigDecimal balanceAfterDebit = currentBalance.subtract(amount);

                if(balanceAfterDebit.compareTo(compareValue) >= 0){
                    boolean hasTransactionID = true;

                    do{
                        System.out.println("Введите уникальный индификатор транзакции:");
                        transaction_uid = scanner.nextLine().trim();

                        /**
                         * Выполняем проверку ID транзакции - есть ли такая в БД + проверку не введён ли просто Enter
                         * */
                        if(transactionDataBase.checkUniqueTransactionById(transaction_uid) && !transaction_uid.isEmpty()){
                            System.out.println("Данный индификатор транзакции - НЕ УНИКАЛЕН или пустой! Попробуйте ещё раз:");
                        }else {
                            playerDataBase.updateBalancePlayer(player.getPlayer_id(), balanceAfterDebit);
                            transactionDataBase.addTransaction(new Transaction(transaction_uid, amount, TypeOperation.DEBIT, player));
                            hasTransactionID = false;
                        }
                    }while (hasTransactionID);
                }else {
                    System.out.println("ОШИБКА: Недостаточно денег для снятия. Ваш остаток на счёте = " + player.getBalance() + ", попробуйте ещё раз!");
                    getInputAmountDebit(player);
                }
            }

        }catch (InputMismatchException e){
            System.out.println("Ввод только чисел, в качестве разделителя вещественного числа - точка. Попробуйте ещё");
            isSuccessful = getInputAmountDebit(player);
        }catch (NumberFormatException e){
            System.out.println("Не верный формат ввода значения debit суммы, попробуйте ещё раз!");
            isSuccessful = getInputAmountDebit(player);
        }

        return isSuccessful;
    }

    public void showHistoryTransaction(Player player){
        List<Transaction> transactions = transactionDataBase.getTransactionsPlayer(player);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        for (Transaction transaction : transactions){
            System.out.println("Сумма транзакции: " + transaction.getAmount()
                    + " бел.руб, тип транзакции: " + transaction.getType_operation()
                    + ", время: " + dateFormat.format(transaction.getDate()));
        }
    }
}
