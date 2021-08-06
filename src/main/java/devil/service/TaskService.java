package devil.service;

import devil.model.Task;

import java.util.List;

public interface TaskService {
    Task createOrUpdate(Task task);
    List<Task> getAll();
    Task findById(Long id);
}
