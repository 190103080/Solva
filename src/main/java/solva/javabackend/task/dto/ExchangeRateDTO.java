package solva.javabackend.task.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeRateDTO {

    private Long id;
    private String currencyPair;
    private BigDecimal closedRate;
    private BigDecimal previousCloseRate;
    private LocalDate date;

    public ExchangeRateDTO(BigDecimal exchangeRate, String fromCurrency, String toCurrency) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getClosedRate() {
        return closedRate;
    }

    public void setClosedRate(BigDecimal closedRate) {
        this.closedRate = closedRate;
    }

    public BigDecimal getPreviousCloseRate() {
        return previousCloseRate;
    }

    public void setPreviousCloseRate(BigDecimal previousCloseRate) {
        this.previousCloseRate = previousCloseRate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
