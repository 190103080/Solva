package solva.javabackend.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solva.javabackend.task.entity.Limit;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LimitRepository extends JpaRepository<Limit, Long> {

    Limit findByClientIdAndExpenseCategory(Long clientId, String expenseCategory);
    Limit findByClientIdAndExpenseCategoryAndLimitCurrencyShortname(Long clientId, String expenseCategory, String limitCurrencyShortname);
    List<Limit> findByClientId(Long clientId);

}
