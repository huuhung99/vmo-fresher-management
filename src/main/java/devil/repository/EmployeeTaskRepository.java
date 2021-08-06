package devil.repository;

import devil.model.EmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTaskRepository extends JpaRepository<EmployeeTask,Long> {
    List<EmployeeTask> findAllByEmployeeId(Long id);
}
