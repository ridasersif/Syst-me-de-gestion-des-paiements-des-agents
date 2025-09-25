package repository;

import config.DatabaseConnection;
import model.Agent;
import model.Department;
import model.enums.AgentType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgentRepositoryImpl implements IAgentRepository {

    private Connection conn;

    public  AgentRepositoryImpl() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }


    @Override
    public void addAgent(Agent agent) {
        String sql = "INSERT INTO Agents (firstName, lastName, email, password, agentType, department_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agent.getFirstName());
            stmt.setString(2, agent.getLastName());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgentType().name());
            stmt.setInt(6, agent.getDepartment().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgent(Agent agent) {
        String sql = "UPDATE Agents SET firstName = ?, lastName = ?, email = ?, password = ?, agentType = ?, department_id = ? WHERE idAgent = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agent.getFirstName());
            stmt.setString(2, agent.getLastName());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getPassword());
            stmt.setString(5, agent.getAgentType().name());
            stmt.setInt(6, agent.getDepartment().getId());
            stmt.setInt(7, agent.getIdAgent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAgent(int agentId) {
        String sql = "DELETE FROM Agents  WHERE idAgent = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,agentId);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Optional<Agent> getAgentById(int agentId) {
        String sql = "SELECT a.*, d.idDepartment, d.name AS deptName " +
                "FROM Agents a JOIN Departments d ON a.department_id = d.idDepartment " +
                "WHERE a.idAgent = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Department department = new Department(
                        rs.getInt("idDepartment"),
                        rs.getString("deptName")
                );
                Agent agent = new Agent(
                        rs.getInt("idAgent"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agentType")),
                        department
                );
                return Optional.of(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Agent> getAgentsByDepartment(int departmentId) {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT a.*, d.idDepartment, d.name AS deptName " +
                "FROM Agents a JOIN Departments d ON a.department_id = d.idDepartment " +
                "WHERE d.idDepartment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Department department = new Department(
                        rs.getInt("idDepartment"),
                        rs.getString("deptName")
                );
                Agent agent = new Agent(
                        rs.getInt("idAgent"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agentType")),
                        department
                );
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

    @Override
    public List<Agent> getAllAgents() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT a.*, d.idDepartment, d.name AS deptName " +
                "FROM Agents a JOIN Departments d ON a.department_id = d.idDepartment";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Department department = new Department(
                        rs.getInt("idDepartment"),
                        rs.getString("deptName")
                );
                Agent agent = new Agent(
                        rs.getInt("idAgent"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        AgentType.valueOf(rs.getString("agentType")),
                        department
                );
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }
}
