import java.sql.*;
import java.util.Scanner;

public class ProductCRUD 
{
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "Sam";
    private static final String PASSWORD = "Sam12345678";

    public static Connection connect() throws SQLException 
    {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
    public static void addProduct(Connection connection, Scanner scanner) 
    {
        try 
        {
            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
            {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.executeUpdate();
                connection.commit();
                System.out.println("Product added successfully.");
            }
        } 
        catch (SQLException e) 
        {
            try 
            { 
                connection.rollback(); 
            } 
            catch (SQLException ex) 
            { 
                ex.printStackTrace(); 
            }
            System.err.println("Error adding product: " + e.getMessage());
        }
    }
    public static void viewProducts(Connection connection) 
    {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                System.out.println("ID: " + rs.getInt("ProductID") + ", Name: " + rs.getString("ProductName") +
                        ", Price: " + rs.getDouble("Price") + ", Quantity: " + rs.getInt("Quantity"));
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error retrieving products: " + e.getMessage());
        }
    }
    public static void updateProduct(Connection connection, Scanner scanner) 
    {
        try 
        {
            System.out.print("Enter Product ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter new Product Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter new Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
            {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.setInt(4, id);
                pstmt.executeUpdate();
                connection.commit();
                System.out.println("Product updated successfully.");
            }
        } 
        catch (SQLException e) 
        {
            try 
            { 
                connection.rollback(); 
            } 
            catch (SQLException ex) 
            { 
                ex.printStackTrace(); 
            }
            System.err.println("Error updating product: " + e.getMessage());
        }
    }
    public static void deleteProduct(Connection connection, Scanner scanner) 
    {
        try 
        {
            System.out.print("Enter Product ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            String sql = "DELETE FROM Product WHERE ProductID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
            {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                connection.commit();
                System.out.println("Product deleted successfully.");
            }
        } 
        catch (SQLException e) 
        {
            try 
            { 
                connection.rollback(); 
            } 
            catch (SQLException ex) 
            { 
                ex.printStackTrace(); 
            }
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = connect()) 
        {
            while (true) 
            {
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) 
                {
                    case 1:
                        addProduct(connection, scanner);
                        break;
                    case 2:
                        viewProducts(connection);
                        break;
                    case 3:
                        updateProduct(connection, scanner);
                        break;
                    case 4:
                        deleteProduct(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}