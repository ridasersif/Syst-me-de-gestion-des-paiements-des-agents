package view;

import controller.DepartmentController;
import model.Agent;
import util.InputUtil;

import java.sql.SQLException;

public class DepartmentMenu {

    private DepartmentController departmentController;
    private Agent currentUser;

    public DepartmentMenu(Agent currentUser) {
        try {
            this.departmentController = new DepartmentController();
            this.currentUser = currentUser;
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erreur lors de la connexion au contrôleur des départements", e);
        }
    }
    public DepartmentMenu(){}

    public void show() {
        while (true) {
            System.out.println("\n==============================");
            System.out.println("       DEPARTMENT MENU");
            System.out.println("==============================");
            System.out.println("1️⃣  Créer un département");
            System.out.println("2️⃣  Modifier un département");
            System.out.println("3️⃣  Supprimer un département");
            System.out.println("4️⃣  Voir tous les départements");
            System.out.println("0️⃣  Retour");
            System.out.println("==============================");

            int choice = InputUtil.readInt("Choisissez une option ");
            switch (choice) {
                case 1 -> addDepartment();
                case 2 -> updateDepartment();
                case 3 -> deleteDepartment();
                case 4 -> viewAllDepartments();
                case 0 -> {
                    System.out.println("Retour au menu principal...");
                    return;
                }
                default -> System.out.println("Choix invalide, veuillez réessayer !");
            }
        }
    }

    private void addDepartment() {
        String deptName = InputUtil.readString("Entrez le nom du département ");
        departmentController.addDepartment(deptName);
        System.out.println("✅ Département \"" + deptName + "\" ajouté avec succès !");
    }

    private void updateDepartment() {
        int deptId = InputUtil.readInt(" Entrez l'ID du département à modifier : ");

        var departmentOpt = departmentController.getDepartmentById(deptId);

        if (departmentOpt.isPresent()) {
            var department = departmentOpt.get();
            System.out.println(" Nom actuel du département : " + department.getName());

            String newName = InputUtil.readString("Entrez le nouveau nom du département : ");

            boolean updated = departmentController.updateDepartment(deptId, newName, currentUser);

            if (updated) {
                System.out.println("✅ Département mis à jour avec succès !");
            } else {
                System.out.println("⚠️ La mise à jour a échoué !");
            }

        } else {
            System.out.println("⚠️ Aucun département trouvé avec l'ID  " + deptId);
        }
    }

    private void deleteDepartment(){
        int deptId = InputUtil.readInt("Entrez l'ID du département à Supprimer ");
        var departmentOpt = departmentController.getDepartmentById(deptId);
            if (departmentOpt.isPresent()) {
                String confirm = InputUtil.readString( "Êtes-vous sûr de vouloir supprimer ce département ? (O/N) : ");
                if(confirm.equalsIgnoreCase("o")){
                    boolean deleted = departmentController.deleteDepartment(deptId,currentUser);
                    if (deleted) {
                        System.out.println("Département supprimé avec succès !");
                    } else {
                        System.out.println("⚠️ Vous n'avez pas les droits ou la suppression a échoué !");
                    }
                }else {
                    System.out.println("❌ Suppression annulée !");
                }
            }else {
                System.out.println("⚠️ Aucun département trouvé avec l'ID  " + deptId);
            }
    }

    private void viewAllDepartments() {
        var departments = departmentController.getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("⚠️ Aucun département trouvé !");
        } else {
            System.out.println("\nListe de tous les départements :");
            System.out.println("ID\tNom");
            System.out.println("---------------------------");
            for (var dept : departments) {
                System.out.println(dept.getId() + "\t" + dept.getName());
            }
        }
    }
}
