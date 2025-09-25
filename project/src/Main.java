import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {


            try {
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();

                String sql = "SELECT * FROM agents";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String dept = rs.getString("department");
                    double salary = rs.getDouble("salary");

                    System.out.println(id + " | " + name + " | " + dept + " | " + salary);
                }

            } catch (SQLException e) {
                System.out.println("Database connection failed: " + e.getMessage());
            }

    }

}
