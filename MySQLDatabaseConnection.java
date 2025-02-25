import java.sql.*;
public class MySQLDatabaseConnection 
{
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "Sam";
    private static final String PASSWORD = "Sam12345678";
    public static void main(String[] args) 
    {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT EmpID, Name, Salary FROM Employee")) 
            {
            System.out.println("EmpID | Name | Salary");
            System.out.println("----------------------");
            while (resultSet.next()) 
            {
                int id = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");
                System.out.println(id + " | " + name + " | " + salary);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
