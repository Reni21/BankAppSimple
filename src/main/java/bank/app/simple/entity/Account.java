package bank.app.simple.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "account_id")
    private Long id;
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
    @Column(nullable = false)
    private AccountType type;

    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key
    private User owner;
    @Column(nullable = false)
    private Currency currency;
    private Double balance = 0.0;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "fromAcc",
            cascade = CascadeType.ALL)
    private List<Transaction> withdrawTransactions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "toAcc",
            cascade = CascadeType.ALL)
    private List<Transaction> depositTransactions = new ArrayList<>();


    public Account(AccountType type, String accountNumber, User owner, Currency currency) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Transaction> getWithdrawTransactions() {
        return Collections.unmodifiableList(withdrawTransactions);
    }

    public void addWithdrawTransaction(Transaction transaction) {
        withdrawTransactions.add(transaction);
    }

    public List<Transaction> getDepositTransactions() {
        return Collections.unmodifiableList(depositTransactions);
    }

    public void addDepositTransactions(Transaction transaction) {
        depositTransactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> commonList = new ArrayList<>();
        commonList.addAll(withdrawTransactions);
        commonList.addAll(depositTransactions);
        return commonList;
    }



    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", type=" + type +
                ", owner=" + owner.getName() +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId()) &&
                getAccountNumber().equals(account.getAccountNumber()) &&
                getType() == account.getType() &&
                getOwner().equals(account.getOwner()) &&
                getCurrency() == account.getCurrency() &&
                Objects.equals(getBalance(), account.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountNumber(), getType(), getOwner(), getCurrency(), getBalance());
    }
}
