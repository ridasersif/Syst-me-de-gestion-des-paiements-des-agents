package controller;

import model.Agent;
import model.Department;
import model.enums.AgentType;
import service.DepartmentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController() throws SQLException {
        this.departmentService = new DepartmentService();
    }

    public boolean addDepartment(String deptName) {
        Department department = new Department(0, deptName);
        return departmentService.addDepartment(department);
    }

    public Optional<Department> getDepartmentById(int id) {
        return departmentService.getDepartmentById(id);
    }

    public boolean updateDepartment(int id, String name, Agent currentUser) {
        if (currentUser.getAgentType() == AgentType.DIRECTOR) {
            Department department = new Department(id, name);
            return departmentService.updateDepartment(department);
        } else {
            System.out.println("❌ Vous n'avez pas les droits pour modifier un département !");
            return false;
        }
    }
    public boolean deleteDepartment(int id ,Agent currentUser) {
        return departmentService.deleteDepartment(id,currentUser);
    }

    public List<Department>getAllDepartments(){
        return departmentService.getAllDepartments();
    }
}
