package solva.javabackend.task.service;

import solva.javabackend.task.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRate saveExchangeRate(ExchangeRate exchangeRate);
    List<ExchangeRate> getExchangeRates();
    BigDecimal getCurrentExchangeRate(String fromCurrencyShortname, String toCurrencyShortname);
    BigDecimal getExchangeRate(String fromCurrency, String toCurrency);

}
