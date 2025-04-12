
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgreSQLCreateTable {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/contacts";
        String username = "postgres";
        String password = "12345";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement stmt =connection.createStatement();
            //table creation
            String createTablesql= "CREATE TABLE login(" + "username varchar(50)  ,"
            	+"pass varchar(50)  )";
            stmt.executeUpdate(createTablesql);
            System.out.println("table successfully created");

            if (connection != null) {
                System.out.println("Connected to the database!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}