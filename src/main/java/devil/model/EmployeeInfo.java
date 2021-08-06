package devil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_employee_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    private String language;
    private String specialization;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id",unique = true)
    private Employee employee;
}
