package solva.javabackend.task.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solva.javabackend.task.dto.LimitDTO;
import solva.javabackend.task.dto.TransactionDTO;
import solva.javabackend.task.entity.Limit;
import solva.javabackend.task.entity.Transaction;
import solva.javabackend.task.repository.TransactionRepository;
import solva.javabackend.task.service.ExchangeRateService;
import solva.javabackend.task.service.LimitService;
import solva.javabackend.task.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LimitService limitService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ModelMapper modelMapper;

    private BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRateService.getCurrentExchangeRate(fromCurrency, toCurrency);
    }

    private boolean isLimitExceeded(Long clientId, String expenseCategory, BigDecimal sumInUSD) {
        LimitDTO limit = limitService.getLimit(clientId, expenseCategory);

        if (limit == null) {
            limit = new LimitDTO();
            limit.setId(clientId);
            limit.setExpenseCategory(expenseCategory);
            limit.setLimitSum(BigDecimal.valueOf(1000));
            limit.setLimitDatetime(LocalDateTime.now());
            limit.setLimitCurrencyShortname("USD");
        }

        return sumInUSD.compareTo(limit.getLimitSum()) > 0;
    }

    @Override
    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    private BigDecimal calculateSumInUSD(Transaction transaction) {
        if ("USD".equals(transaction.getCurrencyShortname())) {
            return transaction.getSum();
        } else {
            BigDecimal exchangeRate = getExchangeRate(transaction.getCurrencyShortname(), "USD");
            return transaction.getSum().multiply(exchangeRate);
        }
    }

    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        BigDecimal exchangeRate = getExchangeRate(transactionDTO.getCurrencyShortName(), "USD");
        BigDecimal sumInUSD = transactionDTO.getSum().multiply(exchangeRate);

        if (isLimitExceeded(transactionDTO.getId(), transactionDTO.getExpenseCategory(), sumInUSD)) {
            transactionDTO.setLimitExceeded(true);
        }

        // Преобразуйте DTO в сущность перед сохранением
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Преобразуйте сохраненную сущность обратно в DTO для возврата
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getTransactionsByClientId(Long clientId) {
        List<Transaction> transactions = transactionRepository.findByClientId(clientId);

        // Преобразуйте список сущностей в список DTO
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsExceedingLimit(Long clientId, String expenseCategory) {
        List<Transaction> transactions = transactionRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory);
        List<TransactionDTO> exceedingLimitTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            BigDecimal sumInUSD = calculateSumInUSD(transaction);

            if (isLimitExceeded(clientId, expenseCategory, sumInUSD)) {
                TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
                transactionDTO.setLimitExceeded(true);
                exceedingLimitTransactions.add(transactionDTO);
            }
        }

        return exceedingLimitTransactions;
    }

    @Override
    public void setLimitExceededFlagForTransactions(Long clientId, String expenseCategory) {
        List<Transaction> transactions = transactionRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory);

        for (Transaction transaction : transactions) {
            BigDecimal sumInUSD = calculateSumInUSD(transaction);

            if (isLimitExceeded(clientId, expenseCategory, sumInUSD)) {
                transaction.setLimitExceeded(true);
            }
        }

        transactionRepository.saveAll(transactions);
    }

    @Override
    public BigDecimal calculateTotalExpenses(Long clientId, String expenseCategory, String currencyShortname) {
        List<Transaction> transactions = transactionRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory);
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getCurrencyShortname().equals(currencyShortname)) {
                totalExpenses = totalExpenses.add(transaction.getSum());
            } else {
                BigDecimal exchangeRate = getExchangeRate(transaction.getCurrencyShortname(), "USD");
                totalExpenses = totalExpenses.add(transaction.getSum().multiply(exchangeRate));
            }
        }

        return totalExpenses;
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

}
