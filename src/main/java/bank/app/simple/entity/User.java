package bank.app.simple.entity;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "personal_number", nullable = false, unique = true)
    private String personalNumber;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "owner",
            cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    public User(String name, String personalNumber) {
        this.personalNumber = personalNumber;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getName() {
        return name;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User  " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personalNumber='" + personalNumber + '\'' + "accounts: ");
        if (!accounts.isEmpty()) {
            for (Account account : accounts) {
                sb.append("id=" + account.getId() + ", ");
                sb.append("currency=" + account.getCurrency() + ", ");
                sb.append("balance=" + account.getBalance());
            }
        }else {
            sb.append("null");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getName().equals(user.getName()) &&
                getPersonalNumber().equals(user.getPersonalNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPersonalNumber());
    }
}
