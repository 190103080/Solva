package solva.javabackend.task.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import solva.javabackend.task.dto.LimitDTO;
import solva.javabackend.task.service.LimitService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/limits")
public class LimitController {

    @Autowired
    private LimitService limitService;

    @GetMapping
    public List<LimitDTO> getLimits() {
        List<LimitDTO> limitDTOs = new ArrayList<>();
        List<LimitDTO> limits = limitService.getLimits();
        for (LimitDTO limit : limits) {
            LimitDTO limitDTO = convertToLimitDTO(limit);
            limitDTOs.add(limitDTO);
        }
        return limitDTOs;
    }

    @PostMapping
    public LimitDTO setExpenselimit(@RequestBody LimitDTO limitDTO) {
        return limitService.setExpenseLimit(limitDTO);
    }

    @GetMapping("/{clientId}/{category}")
    public LimitDTO getLimitByClientIdAndCategory(
            @PathVariable Long clientId,
            @PathVariable String category) {
        return limitService.getLimitByClientIdAndCategory(clientId, category);
    }

    @GetMapping("/{limitId}")
    public void deleteLimit(@PathVariable Long limitId) {
        limitService.deleteLimit(limitId);
    }

    private LimitDTO convertToLimitDTO(LimitDTO limit) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(limit, LimitDTO.class);
    }
}