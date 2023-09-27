package solva.javabackend.task.service;

import solva.javabackend.task.dto.LimitDTO;
import solva.javabackend.task.entity.Limit;

import java.util.List;

public interface LimitService {

    LimitDTO setExpenseLimit(LimitDTO limitDTO);
    List<LimitDTO> getLimits();
    LimitDTO getLimitByClientIdAndCategory(Long clientId, String category);
    List<LimitDTO> getLimitsByClientId(Long clientId);
    void deleteLimit(Long limitId);
    LimitDTO getLimit(Long clientId, String expenseCategory);

}