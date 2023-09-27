package solva.javabackend.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import solva.javabackend.task.entity.ExchangeRate;
import solva.javabackend.task.exceptions.ExchangeRateNotFoundException;
import solva.javabackend.task.repository.ExchangeRateRepository;
import solva.javabackend.task.service.ExchangeRateApiResponse;
import solva.javabackend.task.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${exchangeRate.api.url}")
    private String exchangeRateApiUrl;

    @Override
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public BigDecimal getCurrentExchangeRate(String fromCurrencyShortname, String toCurrencyShortname) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findByFromCurrencyAndToCurrency(
                fromCurrency, toCurrency);

        if (exchangeRateOptional.isPresent()) {
            return exchangeRateOptional.get().getRate();
        }

        String apiUrl = String.format("%s?from=%s&to=%s", exchangeRateApiUrl, fromCurrency, toCurrency);

        try {
            ResponseEntity<ExchangeRateApiResponse> responseEntity = restTemplate.getForEntity(apiUrl, ExchangeRateApiResponse.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ExchangeRateApiResponse responseBody = responseEntity.getBody();
                BigDecimal rate = responseBody.getRate();

                ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, toCurrency, rate);
                exchangeRateRepository.save(exchangeRate);

                return rate;
            } else {
                throw new ExchangeRateNotFoundException("Exchange rate not found for " + fromCurrency + "/" + toCurrency);
            }
        } catch (RestClientException e) {
            throw new ExchangeRateNotFoundException("Failed to retrieve exchange rate data");
        }
    }
}
