import java.sql.*;
import java.util.Scanner;

public class StudentInfo {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "root";
    private static final String PASS = "AGdm@12##";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: addStudent(sc); break;
                case 2: viewStudents(); break;
                case 3: updateStudent(sc); break;
                case 4: deleteStudent(sc); break;
                case 5: System.exit(0);
                default: System.out.println("Invalid option.");
            }
        }
    }


    private static void addStudent(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("INSERT INTO students (name, age) VALUES (?, ?)")) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.executeUpdate();
            System.out.println("Student added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudents() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ": " + rs.getString("name") + ", Age: " + rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent(Scanner sc) {
        System.out.print("Enter student id to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new name: ");
        String name = sc.next();
        System.out.print("Enter new age: ");
        int age = sc.nextInt();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("UPDATE students SET name=?, age=? WHERE id=?")) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Student updated." : "Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter student id to delete: ");
        int id = sc.nextInt();
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE id=?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Student deleted." : "Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}