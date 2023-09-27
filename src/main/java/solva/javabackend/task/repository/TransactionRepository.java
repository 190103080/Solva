package solva.javabackend.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solva.javabackend.task.entity.Transaction;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByClientId(Long clientId);
    List<Transaction> findByClientIdAndExpenseCategory(Long clientId, String expenseCategory);
    List<Transaction> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}
