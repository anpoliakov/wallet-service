package by.anpoliakov.domain;

import java.util.Date;

/**
 * Сущность представляет из себя снятие/пополнение баласа
 * хранит в себе все основные данные операции
 * */
public class Transaction {
    private Double amount;
    private TypeOperation type;
    private Date date;
    private Player relationToPlayer; //информация о игроке кто выполнял операцию

    public Transaction(Double amount, TypeOperation type, Date date, Player relationToPlayer) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.relationToPlayer = relationToPlayer;
    }

    public Double getAmount() {
        return amount;
    }

    public TypeOperation getType() {
        return type;
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
                ", type=" + type +
                ", date=" + date +
                ", relationToPlayer=" + relationToPlayer +
                '}';
    }
}
