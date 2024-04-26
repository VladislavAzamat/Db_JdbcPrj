package aston_org.example.repository;
import aston_org.example.dbConnection.DatabaseInitializer;
import aston_org.example.entity.Credential;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialRepository {

    private Connection connection;
    private final StudentRepository studentRepository = new StudentRepository();

    public CredentialRepository(Connection connection) {
        this.connection = connection;
    }

    public CredentialRepository() {
    }

    public List<Credential> getAllCredentials() throws SQLException {
        List<Credential> credentials = new ArrayList<>();

        String sql = "SELECT * FROM credential";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Credential credential = new Credential(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        studentRepository.getStudentById(resultSet.getInt("student_id"))
                );
                credentials.add(credential);
            }
        }
        return credentials;
    }

    public Credential getCredentialById(int credentialId) throws SQLException {
        Credential credential = null;

        String sql = "SELECT * FROM credential WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, credentialId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    credential = new Credential(
                            resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_number"),
                            studentRepository.getStudentById(resultSet.getInt("student_id"))
                    );
                }
            }
        }
        return credential;
    }

    public Integer addCredential(Credential credential) throws SQLException {
        List<Credential> credentials = getAllCredentials();

        for (Credential c : credentials) {
            if (c.getStudent().getId() == credential.getStudent().getId()) {
                throw new SQLException("Credential with student ID " + credential.getStudent().getId() + " already exists.");
            }
        }
        String sql = "INSERT INTO credential (email, phone_number, student_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, credential.getEmail());
            statement.setString(2, credential.getPhoneNumber());
            statement.setInt(3, credential.getStudent().getId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating credential failed, no ID obtained.");
                }
            }
        }
    }

    public void updateCredential(int credentialId, Credential updatedCredential) throws SQLException {

//        List<Credential> credentials = getAllCredentials();
//
//        for (Credential c : credentials) {
//            if (c.getStudent().getId() != updatedCredential.getStudent().getId()) {
//                throw new SQLException("Credential with student ID " + updatedCredential.getStudent().getId() + " does not exists. You cannot bind this credential with non-existing student!!!");
//            }
//        }

        String sql = "UPDATE credential SET email = ?, phone_number = ?, student_id = ? WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedCredential.getEmail());
            statement.setString(2, updatedCredential.getPhoneNumber());
            statement.setInt(3, updatedCredential.getStudent().getId());
            statement.setInt(4, credentialId);

            statement.executeUpdate();
        }
    }

    public void deleteCredential(int credentialId) throws SQLException {
        String sql = "DELETE FROM credential WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, credentialId);
            statement.executeUpdate();
        }
    }




    public boolean  studentExists(int studentId) {
        String sql = "SELECT COUNT(*) FROM student WHERE id = ?";
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
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
