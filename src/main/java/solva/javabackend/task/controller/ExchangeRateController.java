package solva.javabackend.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solva.javabackend.task.dto.ExchangeRateDTO;
import solva.javabackend.task.entity.ExchangeRate;
import solva.javabackend.task.exceptions.ExchangeRateNotFoundException;
import solva.javabackend.task.service.ExchangeRateApiResponse;
import solva.javabackend.task.service.ExchangeRateService;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/exchange-rate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping(value = "/current")
    public ResponseEntity<ExchangeRateDTO> getCurrentExchangeRate(
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency) {
        try {
            BigDecimal exchangeRate = exchangeRateService.getCurrentExchangeRate(fromCurrency, toCurrency);
            ExchangeRateDTO response = new ExchangeRateDTO(exchangeRate, fromCurrency, toCurrency);
            return ResponseEntity.ok(response);
        } catch (ExchangeRateNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<String> handleExchangeRateNotFoundException(ExchangeRateNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}
