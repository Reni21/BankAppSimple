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
    @JoinColumn(name = "from_account_id", referencedColumnName = "account_id")
    private Account fromAcc;
    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "account_id")
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
        String from;
        String to;
        if (fromAcc == null){
            from = "null";
        } else {
            from = fromAcc.getOwner().getName();
        }

        if (toAcc == null){
            to = "null";
        } else {
            to = toAcc.getOwner().getName();
        }

        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", fromAcc=" + from +
                ", toAcc=" + to +
                ", sum=" + sum +
                ", time=" + time +
                '}';
    }
}
