package devil.service.impl;

import devil.common.error.BadRequestException;
import devil.model.Task;
import devil.repository.TaskRepository;
import devil.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createOrUpdate(Task request) {
        if(request.getId()==null){
            // create
            return taskRepository.save(request);
        }
        Optional<Task> op = taskRepository.findById(request.getId());
        if(!op.isPresent()){
            throw new BadRequestException("task id does not exit!");
        }
        Task task = op.get();
        if(request.getName()!=null){
            task.setName(request.getName());
        }
        if(request.getContent()!=null){
            task.setContent(request.getContent());
        }
        if(request.getDescription()!=null){
            task.setDescription(request.getDescription());
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> op = taskRepository.findById(id);
        return op.isPresent()?op.get():null;
    }
}
