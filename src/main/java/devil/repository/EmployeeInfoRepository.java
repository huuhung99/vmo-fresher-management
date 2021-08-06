package devil.repository;

import devil.model.Employee;
import devil.model.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo,Long> {
    @Query("SELECT e FROM EmployeeInfo e WHERE e.employee.id=?1")
    EmployeeInfo findEmployeeInfoByEmployeeId(Long id);
    @Query("SELECT e FROM EmployeeInfo e "
            +" inner join Employee em on e.employee.id=em.id "+
            " WHERE e.language like %:key% or em.email like %:key% or em.lastName like %:key%")
    List<EmployeeInfo> filter(@RequestParam String key);
    @Query("SELECT e FROM EmployeeInfo e WHERE e.department.id=?1")
    List<EmployeeInfo> findEmployeeInfoByDepartmentId(Long id);
}
