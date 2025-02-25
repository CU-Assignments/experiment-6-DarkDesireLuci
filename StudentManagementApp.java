import java.sql.*;
import java.util.Scanner;

class Student 
{
    private int studentID;
    private String name;
    private String department;
    private double marks;
    public Student(int studentID, String name, String department, double marks) 
    {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

class StudentController 
{
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "Sam";
    private static final String PASSWORD = "Sam12345678";

    public static Connection connect() throws SQLException 
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void addStudent(Connection connection, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
        {
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setDouble(3, marks);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }
    public static void viewStudents(Connection connection) throws SQLException 
    {
        String sql = "SELECT * FROM Student";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                System.out.println("ID: " + rs.getInt("StudentID") + ", Name: " + rs.getString("Name") +
                        ", Department: " + rs.getString("Department") + ", Marks: " + rs.getDouble("Marks"));
            }
        }
    }
    public static void updateStudent(Connection connection, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter new Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
        {
            pstmt.setString(1, name);
            pstmt.setString(2, department);
            pstmt.setDouble(3, marks);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Student updated successfully.");
        }
    }
    public static void deleteStudent(Connection connection, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        String sql = "DELETE FROM Student WHERE StudentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }
}

public class StudentManagementApp 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = StudentController.connect()) 
        {
            while (true) 
            {
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) 
                {
                    case 1:
                        StudentController.addStudent(connection, scanner);
                        break;
                    case 2:
                        StudentController.viewStudents(connection);
                        break;
                    case 3:
                        StudentController.updateStudent(connection, scanner);
                        break;
                    case 4:
                        StudentController.deleteStudent(connection, scanner);
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
