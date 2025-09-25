import repository.DepartmentRepositoryImpl;
import repository.AgentRepositoryImpl;
import model.Department;
import model.Agent;
import model.enums.AgentType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws SQLException {

        DepartmentRepositoryImpl deptRepo = new DepartmentRepositoryImpl();
        AgentRepositoryImpl agentRepo = new AgentRepositoryImpl();
        Scanner sc = new Scanner(System.in);

        System.out.print("First name: ");
        String firstName = sc.nextLine();

        System.out.print("Last name: ");
        String lastName = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Type (MANAGER / STAFF): ");
        AgentType type = AgentType.valueOf(sc.nextLine().toUpperCase());


        List<Department> departments = deptRepo.getAllDepartments();
        System.out.println("Liste des Departments disponibles:");
        for (Department d : departments) {
            System.out.println(d.getId() + " - " + d.getName());
        }

        System.out.print("Choisissez Department ID pour cet agent: ");
        int deptId = sc.nextInt();


        Optional<Department> deptOpt = deptRepo.getDepartmentById(deptId);
        if(deptOpt.isPresent()) {
            Department department = deptOpt.get();


            Agent newAgent = new Agent(0, firstName, lastName, email, password, type, department);
            agentRepo.addAgent(newAgent);

            System.out.println("Agent ajouté avec succès dans le department: " + department.getName());

        } else {
            System.out.println("Department ID invalide!");
        }

        sc.close();
    }
}
