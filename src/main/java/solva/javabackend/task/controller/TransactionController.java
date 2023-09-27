package solva.javabackend.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import solva.javabackend.task.dto.TransactionDTO;
import solva.javabackend.task.entity.Transaction;
import solva.javabackend.task.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public TransactionDTO saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{clientId}")
    public List<TransactionDTO> getTransactionsByClientId(@PathVariable Long clientId) {
        return transactionService.getTransactionsByClientId(clientId);
    }

    @GetMapping("/{clientId}/{expenseCategory}/exceeding-limit")
    public List<TransactionDTO> getTransactionsExceedingLimit(
            @PathVariable Long clientId,
            @PathVariable String expenseCategory) {
        return transactionService.getTransactionsExceedingLimit(clientId, expenseCategory);
    }

    @PutMapping("/set-limit-exceeded-flag/{clientId}/{expenseCategory}")
    public void setLimitExceededFlagForTransactions(
            @PathVariable Long clientId,
            @PathVariable String expenseCategory) {
        transactionService.setLimitExceededFlagForTransactions(clientId, expenseCategory);
    }

    @GetMapping("/total-expenses/{clientId}/{expenseCategory}/{currencyShortname}")
    public BigDecimal calculateTotalExpenses(
            @PathVariable Long clientId,
            @PathVariable String expenseCategory,
            @PathVariable String currencyShortname) {
        return transactionService.calculateTotalExpenses(clientId, expenseCategory, currencyShortname);
    }

    @GetMapping("/date-range")
    public List<TransactionDTO> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }

}
