package solva.javabackend.task.service;

import solva.javabackend.task.dto.TransactionDTO;
import solva.javabackend.task.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionDTO saveTransaction(TransactionDTO transactionDTO);
    List<TransactionDTO> getTransactions();
    List<TransactionDTO> getTransactionsByClientId(Long clientId);
    List<TransactionDTO> getTransactionsExceedingLimit(Long clientId, String expenseCategory);
    void setLimitExceededFlagForTransactions(Long clientId, String expenseCategory);
    BigDecimal calculateTotalExpenses(Long clientId, String expenseCategory, String currencyShortname);
    List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
