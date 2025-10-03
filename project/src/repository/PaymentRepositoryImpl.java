package repository;

import config.DatabaseConnection;
import model.Agent;
import model.Department;
import model.Payment;
import model.enums.AgentType;
import model.enums.PaymentType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentRepositoryImpl implements IPaymentRepository {

    private Connection conn;
    private AgentRepositoryImpl agentRepo;

    public PaymentRepositoryImpl() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
        this.agentRepo = new AgentRepositoryImpl();
    }

    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO Payment (paymentType, amount, date, reason, agent_id, conditionValidated) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getPaymentType().name());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, java.sql.Date.valueOf(payment.getDate()));
            stmt.setString(4, payment.getReason());
            stmt.setInt(5, payment.getAgent().getIdAgent());
            stmt.setBoolean(6, payment.getConditionValidated());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE Payment SET paymentType=?, amount=?, date=?, reason=?, conditionValidated=? WHERE idPayment=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getPaymentType().name());
            stmt.setDouble(2, payment.getAmount());
            stmt.setDate(3, java.sql.Date.valueOf(payment.getDate()));
            stmt.setString(4, payment.getReason());
            stmt.setBoolean(5, payment.getConditionValidated());
            stmt.setInt(6, payment.getIdPayment());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Payment> getPaymentById(int idPayment) {
        String sql = "SELECT * FROM Payment WHERE idPayment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPayment);

            var rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idPayment");
                PaymentType type = PaymentType.valueOf(rs.getString("paymentType"));
                double amount = rs.getDouble("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                String reason = rs.getString("reason");
                boolean conditionValidated = rs.getBoolean("conditionValidated");
                int agentId = rs.getInt("agent_id");
                Optional<Agent> agentOpt = agentRepo.getAgentById(agentId);

                if (agentOpt.isPresent()) {
                    Payment payment = new Payment(
                            id,
                            type,
                            amount,
                            date,
                            reason,
                            agentOpt.get(),
                            conditionValidated
                    );
                    return Optional.of(payment);
                } else {
                    System.out.println("⚠ Aucun agent trouvé avec l'ID : " + agentId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePayment(int idPayment) {
        String sql = "DELETE FROM Payment WHERE idPayment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPayment);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Payment> getAllPaiement() {
        String sql = "SELECT p.idPayment, p.paymentType, p.amount, p.date, p.reason, p.conditionValidated, " +
                "a.idAgent, a.firstName, a.lastName, a.email, a.password, a.agentType, " +
                "d.idDepartment, d.name as departmentName " +
                "FROM Payment p " +
                "JOIN Agents a ON p.agent_id = a.idAgent " +
                "LEFT JOIN Department d ON a.department_id = d.idDepartment";

        List<Payment> payments = new java.util.ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("idPayment");
                PaymentType type = PaymentType.valueOf(rs.getString("paymentType"));
                double amount = rs.getDouble("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                String reason = rs.getString("reason");
                boolean conditionValidated = rs.getBoolean("conditionValidated");

                int agentId = rs.getInt("idAgent");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                AgentType agentType = AgentType.valueOf(rs.getString("agentType").toUpperCase());

                int departmentId = rs.getInt("idDepartment");
                String departmentName = rs.getString("departmentName");

                Department department = new Department(departmentId, departmentName);
                Agent agent = new Agent(agentId, firstName, lastName, email, password, agentType, department);
                Payment payment = new Payment(id, type, amount, date, reason, agent, conditionValidated);
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    @Override
    public List<Payment> findByAgentId(int agentId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT p.idPayment, p.paymentType, p.amount, p.date, p.reason, p.conditionValidated, " +
                "a.idAgent, a.firstName, a.lastName, a.email, a.password, a.agentType, " +
                "d.idDepartment, d.name as departmentName " +
                "FROM Payment p " +
                "JOIN Agents a ON p.agent_id = a.idAgent " +
                "LEFT JOIN Department d ON a.department_id = d.idDepartment " +
                "WHERE a.idAgent = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPayment");
                PaymentType type = PaymentType.valueOf(rs.getString("paymentType"));
                double amount = rs.getDouble("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                String reason = rs.getString("reason");
                boolean conditionValidated = rs.getBoolean("conditionValidated");

                // Agent
                int idAg = rs.getInt("idAgent");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                AgentType agentType = AgentType.valueOf(rs.getString("agentType").toUpperCase());

                // Department
                int depId = rs.getInt("idDepartment");
                String depName = rs.getString("departmentName");
                Department department = new Department(depId, depName);

                Agent agent = new Agent(idAg, firstName, lastName, email, password, agentType, department);
                Payment payment = new Payment(id, type, amount, date, reason, agent, conditionValidated);

                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

}
