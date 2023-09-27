package solva.javabackend.task.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_limit")
@Data
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "expenseCategory")
    private String expenseCategory;

    @Column(name = "limitSum")
    private BigDecimal limitSum;

    @Column(name = "limitDatetime")
    private LocalDateTime limitDatetime;

    @Column(name = "limitCurrencyShortname")
    private String limitCurrencyShortname;


    public Limit(Long clientId, String expenseCategory, BigDecimal limit, LocalDateTime now, String usd) {
        this.clientId = clientId;
        this.expenseCategory = expenseCategory;
        this.limitSum = limit;
        this.limitDatetime = now;
        this.limitCurrencyShortname = usd;
    }
}
