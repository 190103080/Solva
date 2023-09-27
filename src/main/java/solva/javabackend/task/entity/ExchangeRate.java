package solva.javabackend.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_exchangerate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "currencyPair")
    private String currencyPair;

    @Column(name = "closeRate")
    private BigDecimal closeRate;

    @Column(name = "previousCloseRate")
    private BigDecimal previousCloseRate;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rate")
    private BigDecimal rate;

    public ExchangeRate(String fromCurrency, String toCurrency, BigDecimal rate) {
        this.currencyPair = fromCurrency + "/" +toCurrency;
        this.closeRate = rate;
    }
}
