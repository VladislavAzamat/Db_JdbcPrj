package aston_org.example.repository;

import aston_org.example.dbConnection.DatabaseInitializer;
import aston_org.example.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private static final String GET_ALL_STUDENTS = "SELECT * FROM student";
    private static final String GET_STUDENT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String ADD_STUDENT = "INSERT INTO student (first_name, last_name, course_id) VALUES (?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE student SET first_name = ?, last_name = ?, course_id = ? WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    private static final String GET_STUDENTS_BY_COURSE_ID = "SELECT * FROM student WHERE course_id = ?";

    private Connection connection;
    private final CourceRepository courceRepository = new CourceRepository();

    public StudentRepository(Connection connection) {
        this.connection = connection;
    }

    public StudentRepository() {
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_STUDENTS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("course_id")
                );
                students.add(student);
            }
        }
        return students;
    }

    public Student getStudentById(int studentId) throws SQLException {
        Student student = null;

        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_ID)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getInt("course_id"));
                    return student;
                }
            }
        }
        return null;
    }

    public int addStudent(Student student) throws SQLException {
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_STUDENT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            Integer courceId = student.getCourceId();
            System.out.println(courceId);
            if (courceId != null) {
                statement.setInt(3, courceId);
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating student failed, no ID obtained.");
                }
            }
        }
//        return -1;
    }

    public void updateStudent(int studentId, Student updatedStudent) throws SQLException {
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT)) {
            statement.setString(1, updatedStudent.getFirstName());
            statement.setString(2, updatedStudent.getLastName());
            statement.setInt(3, updatedStudent.getCourceId());
            statement.setInt(4, studentId);
            statement.executeUpdate();
        }
    }

    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
    }

    public static List<Student> getStudentsByCourseId(int courseId) {

        List<Student> students = new ArrayList<>();
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STUDENTS_BY_COURSE_ID)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public static Student getStudent(int studentId) throws SQLException {
        Student student = null;

        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_ID)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getInt("course_id"));
                    return student;
                }
            }
        }
        return null;
    }

    public boolean courseExists(int courseId) {
        String sql = "SELECT COUNT(*) FROM course WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
