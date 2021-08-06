package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.controller.request.EmployeeTaskRequest;
import devil.dto.AverageScoreDto;
import devil.dto.EmployeeDto;
import devil.dto.EmployeeTaskDto;
import devil.dto.mapper.GenericMapper;
import devil.model.Employee;
import devil.model.EmployeeTask;
import devil.model.Task;
import devil.repository.EmployeeRepository;
import devil.repository.EmployeeTaskRepository;
import devil.repository.TaskRepository;
import devil.service.EmployeeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeTaskServiceImpl implements EmployeeTaskService {
    @Autowired
    private EmployeeTaskRepository employeeTaskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    GenericMapper genericMapper;
    @Override
    public EmployeeTaskDto createOrUpdate(EmployeeTaskRequest request) {

        EmployeeTask employeeTask=genericMapper.mapToType(request,EmployeeTask.class);
        if(request.getEmployeeId()!=null){
            Optional<Employee> opEmployee = employeeRepository.findById(request.getEmployeeId());
            if(!opEmployee.isPresent()){
                throw new BadRequestException("Emploee does not exit!");
            }
            employeeTask.setEmployee(opEmployee.get());
        }

        if(request.getTaskId()!=null){
            Optional<Task> opTask = taskRepository.findById(request.getTaskId());
            if(!opTask.isPresent()){
                throw new BadRequestException("Task does not exit!");
            }
            employeeTask.setTask(opTask.get());
        }
        employeeTask.setCreateAt(Instant.now());
        // id null create
        if(request.getId()==null){
            return genericMapper.mapToType(employeeTaskRepository.save(employeeTask),EmployeeTaskDto.class);
        }
        // update
        Optional<EmployeeTask> opEmployeeTask = employeeTaskRepository.findById(request.getId());
        if(!opEmployeeTask.isPresent()){
            throw new BadRequestException("Task id invalid!");
        }
        EmployeeTask employeeTaskUpdate = opEmployeeTask.get();
        if(employeeTask.getEmployee()!=null){
            employeeTaskUpdate.setEmployee(employeeTask.getEmployee());
        }
        if(employeeTask.getTask()!=null){
            employeeTaskUpdate.setTask(employeeTask.getTask());
        }
        if(employeeTask.getNote()!=null){
            employeeTaskUpdate.setNote(employeeTaskUpdate.getNote());
        }
        if(employeeTask.getScore()!=null){
            employeeTaskUpdate.setScore(employeeTask.getScore());
        }
        employeeTaskUpdate.setCreateAt(Instant.now());
        return genericMapper.mapToType(employeeTaskRepository.save(employeeTaskUpdate),EmployeeTaskDto.class);
    }

    @Override
    public List<EmployeeTaskDto> getAllByEmployeeId(Long id) {
        return genericMapper.mapToListOfType(employeeTaskRepository.findAllByEmployeeId(id),EmployeeTaskDto.class);
    }

    @Override
    public List<AverageScoreDto> getAllAverageScore() {
        Map<Employee, List<EmployeeTask>> group = employeeTaskRepository.findAll().stream()
                .collect(Collectors.groupingBy(EmployeeTask::getEmployee));
        List<Employee> employees = employeeRepository.findAll();
        List<AverageScoreDto> averageScoreDtos=new ArrayList<>();
        for(int i=0;i<employees.size();i++){
            AverageScoreDto averageScoreDto=new AverageScoreDto();
            if(group.containsKey(employees.get(i))){
                List<EmployeeTask> employeeTasks = group.get(employees.get(i));
                List<Task> tasks=new ArrayList<>();
                Float averageScore=0F;
                for(int j=0;j<employeeTasks.size();j++){
                    averageScore+=employeeTasks.get(j).getScore();
                    tasks.add(employeeTasks.get(j).getTask());
                }
                averageScoreDto.setAverageScore(averageScore/employeeTasks.size());
                averageScoreDto.setEmployeeDto(genericMapper.mapToType(employees.get(i), EmployeeDto.class));
                averageScoreDto.setTasks(tasks);
                averageScoreDtos.add(averageScoreDto);
            }
            else{
                averageScoreDto.setEmployeeDto(genericMapper.mapToType(employees.get(i), EmployeeDto.class));
                averageScoreDtos.add(averageScoreDto);
            }
        }
        Collections.sort(averageScoreDtos, new Comparator<AverageScoreDto>() {
            @Override
            public int compare(AverageScoreDto o1, AverageScoreDto o2) {
                if(o1.getAverageScore()==null){
                    if(o2.getAverageScore()==null){
                        return o1.getEmployeeDto().getLastName().compareTo(o2.getEmployeeDto().getLastName());
                    }
                    else return 1;
                }
                if(o2.getAverageScore()==null){
                    if(o1.getAverageScore()==null){
                        return o2.getEmployeeDto().getLastName().compareTo(o1.getEmployeeDto().getLastName());
                    }
                    else return -1;
                }
                return o1.getAverageScore()<o2.getAverageScore()?1:-1;
            }
        });
        return averageScoreDtos;
    }

    @Override
    public AverageScoreDto getAverageScoreByEmployeeId(Long id) {
        AverageScoreDto averageScoreDto=new AverageScoreDto();
        List<EmployeeTask> employeeTasks = employeeTaskRepository.findAllByEmployeeId(id);
        List<Task> tasks=new ArrayList<>();
        Float averageScore=0F;
        for(int j=0;j<employeeTasks.size();j++){
            averageScore+=employeeTasks.get(j).getScore();
            tasks.add(employeeTasks.get(j).getTask());
        }
        averageScoreDto.setAverageScore(averageScore/employeeTasks.size());
        averageScoreDto.setEmployeeDto(genericMapper.mapToType(employeeTasks.get(0).getEmployee(), EmployeeDto.class));
        averageScoreDto.setTasks(tasks);
        return averageScoreDto;
    }

}
