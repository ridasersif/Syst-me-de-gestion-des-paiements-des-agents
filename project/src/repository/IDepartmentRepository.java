package repository;

import model.Department;
import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {
    void addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(int departmentId);
    Optional<Department> getDepartmentById(int departmentId);
    List<Department> getAllDepartments();
}
