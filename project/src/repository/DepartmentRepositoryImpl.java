package repository;

import config.DatabaseConnection;
import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryImpl implements IDepartmentRepository {
    private Connection conn;

    public DepartmentRepositoryImpl() throws SQLException{
        this.conn = DatabaseConnection.getConnection();
    }
    @Override
    public void addDepartment(Department department) {
        String sql = "INSERT INTO Department (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, department.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateDepartment(Department department) {
        String sql = "UPDATE Department SET name = ? WHERE idDepartment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteDepartment(int departmentId) {
        String sql = "DELETE FROM Department WHERE idDepartment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Department> getDepartmentById(int departmentId) {
        String sql = "SELECT * FROM Department WHERE idDepartment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Department department = new Department(
                        rs.getInt("idDepartment"),
                        rs.getString("name")
                );
                return Optional.of(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Department";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Department department = new Department(
                        rs.getInt("idDepartment"),
                        rs.getString("name")
                );
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

}
