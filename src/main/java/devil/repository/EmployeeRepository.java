package devil.repository;

import devil.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeByUserName(String useName);
}
