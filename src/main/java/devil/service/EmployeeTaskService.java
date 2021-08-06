package devil.service;

import devil.controller.request.EmployeeTaskRequest;
import devil.dto.AverageScoreDto;
import devil.dto.EmployeeTaskDto;

import java.util.List;

public interface EmployeeTaskService {
    EmployeeTaskDto createOrUpdate(EmployeeTaskRequest request);
    List<EmployeeTaskDto> getAllByEmployeeId(Long id);
    List<AverageScoreDto> getAllAverageScore();
    AverageScoreDto getAverageScoreByEmployeeId(Long id);
}
