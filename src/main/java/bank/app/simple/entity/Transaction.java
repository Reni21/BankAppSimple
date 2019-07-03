package bank.app.simple.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account fromAcc;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account toAcc;
    private Double sum;
    private LocalDateTime time;

    public Transaction(TransactionType type, Account fromAcc, Account toAcc, Double sum, LocalDateTime time) {
        this.type = type;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.sum = sum;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", fromAcc=" + fromAcc.getOwner() +
                ", toAcc=" + toAcc.getOwner() +
                ", sum=" + sum +
                ", time=" + time +
                '}';
    }
}
