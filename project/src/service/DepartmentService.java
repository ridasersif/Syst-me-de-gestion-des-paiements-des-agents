package service;

import model.Agent;
import model.Department;
import model.enums.AgentType;
import repository.DepartmentRepositoryImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentService {

    private DepartmentRepositoryImpl departmentRepo;

    public DepartmentService() throws SQLException {
        this.departmentRepo = new DepartmentRepositoryImpl();
    }

    public boolean addDepartment(Department department) {
        if (department.getName() == null || department.getName().trim().isEmpty()) {
            System.out.println("Le nom du département ne peut pas être vide !");
            return false;
        }

        for (Department d : departmentRepo.getAllDepartments()) {
            if (d.getName().equalsIgnoreCase(department.getName().trim())) {
                System.out.println("Ce département existe déjà !");
                return false;
            }
        }

        departmentRepo.addDepartment(department);
        return true;
    }

    public Optional<Department> getDepartmentById(int id) {
        return departmentRepo.getDepartmentById(id);
    }

    public boolean updateDepartment(Department department) {
        if (department.getName() == null || department.getName().trim().isEmpty()) {
            System.out.println("Le nom du département ne peut pas être vide !");
            return false;
        }

        for (Department d : departmentRepo.getAllDepartments()) {
            if (d.getName().equalsIgnoreCase(department.getName().trim())
                    && d.getId() != department.getId()) {
                System.out.println("Ce département existe déjà !");
                return false;
            }
        }

        departmentRepo.updateDepartment(department);
        return true;
    }


    public boolean deleteDepartment(int departmentId, Agent currentUser) {

        if(currentUser.getAgentType() != AgentType.DIRECTOR) {
            System.out.println("Vous n'avez pas les droits pour supprimer un département !");
            return false;
        }
        Optional<Department> deptOpt = departmentRepo.getDepartmentById(departmentId);
        if(deptOpt.isEmpty()) {
            System.out.println("Département introuvable !");
            return false;
        }
        departmentRepo.deleteDepartment(departmentId);
        return true;
    }

    public List<Department> getAllDepartments() {
        return departmentRepo.getAllDepartments();
    }
}
