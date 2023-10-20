package by.anpoliakov.domain;

import java.util.Date;

/**
 * Сущность представляет из себя снятие/пополнение баласа
 * хранит в себе все основные данные операции
 * */
public class Transaction {
    private int transaction_id;
    private String transaction_uid;
    private Double amount;
    private TypeOperation typeOperation;
    private Date date;
    private Player relationToPlayer; //информация о игроке кто выполнял операцию

    public Transaction(Double amount, TypeOperation typeOperation, Date date, Player relationToPlayer) {
        this.amount = amount;
        this.typeOperation = typeOperation;
        this.date = date;
        this.relationToPlayer = relationToPlayer;
    }

    public Transaction(String transaction_uid, Double amount, TypeOperation type_operation, Player player) {
        this.transaction_uid = transaction_uid;
        this.amount = amount;
        this.typeOperation = type_operation;
        this.date = new Date();
        this.relationToPlayer = player;
    }

    public Transaction(int transaction_id, String transaction_uid, Double amount, String name_operation, java.sql.Date date, Player player) {
        this.transaction_id = transaction_id;
        this.transaction_uid = transaction_uid;
        this.amount = amount;
        this.typeOperation = TypeOperation.valueOf(name_operation);
        this.date = date;
        this.relationToPlayer = player;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_uid() {
        return transaction_uid;
    }

    public void setTransaction_uid(String transaction_uid) {
        this.transaction_uid = transaction_uid;
    }

    public Double getAmount() {
        return amount;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Player getRelationToPlayer() {
        return relationToPlayer;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", type=" + typeOperation +
                ", date=" + date +
                ", relationToPlayer=" + relationToPlayer +
                '}';
    }
}
