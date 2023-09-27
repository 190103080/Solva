package solva.javabackend.task.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "clienId")
    private Long clientId;

    @Column(name = "accountFrom")
    private Long accountFrom;

    @Column(name = "accountTo")
    private Long accountTo;

    @Column(name = "currencyShortname")
    private String currencyShortname;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "expenseCategory")
    private String expenseCategory;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

}
