package by.anpoliakov.domain;

/**
 * Сущность представляет из себя снятие/пополнение баласа
 * хранит в себе все основные данные операции
 * */
public class Transaction {
    private Double amount;
    private TypeOperation type;
    private String date;
    /** кто выполнял операцию*/
    private Player relationToPlayer;

    public Transaction(Double amount, TypeOperation type, String date, Player relationToPlayer) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.relationToPlayer = relationToPlayer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TypeOperation getType() {
        return type;
    }

    public void setType(TypeOperation type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Player getRelationToPlayer() {
        return relationToPlayer;
    }

    public void setRelationToPlayer(Player relationToPlayer) {
        this.relationToPlayer = relationToPlayer;
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
