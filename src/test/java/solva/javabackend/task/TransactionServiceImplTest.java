package solva.javabackend.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import solva.javabackend.task.entity.Limit;
import solva.javabackend.task.entity.Transaction;
import solva.javabackend.task.repository.TransactionRepository;
import solva.javabackend.task.service.ExchangeRateService;
import solva.javabackend.task.service.LimitService;
import solva.javabackend.task.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LimitService limitService;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Test
    public void testSetLimitExceededFlagForTransactions() {
        // Создаем фиктивного клиента и транзакции
        Long clientId = 1L;
        String expenseCategory = "food";
        BigDecimal limit = BigDecimal.valueOf(1000);
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setClientId(clientId);
        transaction1.setExpenseCategory(expenseCategory);
        transaction1.setCurrencyShortname("USD");
        transaction1.setSum(BigDecimal.valueOf(1200));
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setClientId(clientId);
        transaction2.setExpenseCategory(expenseCategory);
        transaction2.setCurrencyShortname("USD");
        transaction2.setSum(BigDecimal.valueOf(800));
        transactions.add(transaction2);

        // Мокируем поведение сервисов
        when(transactionRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory))
                .thenReturn(transactions);
        when(exchangeRateService.getCurrentExchangeRate("USD", "USD"))
                .thenReturn(BigDecimal.ONE);

        // Запускаем метод, который должен выставить флаги limit_exceeded
        transactionService.setLimitExceededFlagForTransactions(clientId, expenseCategory);

        // Проверяем, что флаги выставлены правильно
        assertTrue(transaction1.isLimitExceeded());
        assertFalse(transaction2.isLimitExceeded());
    }


}
