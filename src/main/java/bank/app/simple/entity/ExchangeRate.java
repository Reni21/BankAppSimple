package bank.app.simple.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "rate_id")
    private Long id;
    @Column(unique = true)
    private Currency currency;
    private Double buyRate;
    private Double sellRate;

    public ExchangeRate(Currency currency, Double buyRate, Double sellRate) {
        this.currency = currency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }
}
