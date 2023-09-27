package solva.javabackend.task.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solva.javabackend.task.dto.LimitDTO;
import solva.javabackend.task.entity.Limit;
import solva.javabackend.task.repository.LimitRepository;
import solva.javabackend.task.service.LimitService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LimitServiceImpl implements LimitService {

    @Autowired
    private LimitRepository limitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LimitDTO setExpenseLimit(LimitDTO limitDTO) {
        Limit existingLimit = limitRepository.findByClientIdAndExpenseCategoryAndLimitCurrencyShortname(
                limitDTO.getId(), limitDTO.getExpenseCategory(), limitDTO.getLimitCurrencyShortname()
        );

        if (existingLimit != null) {
            existingLimit.setLimitSum(limitDTO.getLimitSum());
            existingLimit.setLimitDatetime(LocalDateTime.now());
            limitRepository.save(existingLimit);

            return modelMapper.map(existingLimit, LimitDTO.class);
        } else {
            Limit limit = modelMapper.map(limitDTO, Limit.class);
            limit.setLimitDatetime(LocalDateTime.now());
            limitRepository.save(limit);

            return modelMapper.map(limit, LimitDTO.class);
        }
    }

    @Override
    public List<LimitDTO> getLimits() {
        List<Limit> limits = limitRepository.findAll();
        return limits.stream()
                .map(limit -> modelMapper.map(limit, LimitDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LimitDTO getLimitByClientIdAndCategory(Long clientId, String expenseCategory) {
        Limit limit = limitRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory);
        return modelMapper.map(limit, LimitDTO.class);
    }

    @Override
    public List<LimitDTO> getLimitsByClientId(Long clientId) {
        List<Limit> limits = limitRepository.findByClientId(clientId);
        return limits.stream()
                .map(limit -> modelMapper.map(limit, LimitDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLimit(Long limitId) {
        limitRepository.deleteById(limitId);
    }

    @Override
    public LimitDTO getLimit(Long clientId, String expenseCategory) {
        Limit limit = limitRepository.findByClientIdAndExpenseCategory(clientId, expenseCategory);
        return modelMapper.map(limit, LimitDTO.class);
    }
}