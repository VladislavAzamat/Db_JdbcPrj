package aston_org.example.repository;

import aston_org.example.dbConnection.DatabaseInitializer;
import aston_org.example.entity.Cource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourceRepository {

    private final String GET_ALL_COURCES = "SELECT * FROM course";
    private final String GET_COURSE_BY_ID = "SELECT * FROM course WHERE id = ?";
    private final String ADD_COURSE = "INSERT INTO course (title, duration, price) VALUES (?, ?, ?)";
    private final String UPDATE_COURSE = "UPDATE course SET title = ?, duration = ?, price = ? WHERE id = ?";
    private final String DELETE_COURSE = "DELETE FROM course WHERE id = ?";

    private Connection connection;
    public CourceRepository(Connection connection) {
        this.connection = connection;
    }

    public CourceRepository() {
    }

    public List<Cource> getAllCources() {

        List<Cource> cources = new ArrayList<>();

        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_COURCES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Cource cource = new Cource();
                cource.setId(resultSet.getInt("id"));
                cource.setTitle(resultSet.getString("title"));
                cource.setDuration(resultSet.getFloat("duration"));
                cource.setPrice(resultSet.getInt("price"));
                cources.add(cource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cources;
    }

    public Cource getCourceById(int courceId)  {
        Cource cource = null;
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COURSE_BY_ID)) {
            statement.setInt(1, courceId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cource = new Cource();
                    cource.setId(resultSet.getInt("id"));
                    cource.setTitle(resultSet.getString("title"));
                    cource.setDuration(resultSet.getFloat("duration"));
                    cource.setPrice(resultSet.getInt("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cource;
    }

    public int addCource(Cource cource)  {
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_COURSE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, cource.getTitle());
            statement.setFloat(2, cource.getDuration());
            statement.setInt(3, cource.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating course failed, no ID obtained.");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateCource(int courceId, Cource updatedCource) {
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)) {
            statement.setString(1, updatedCource.getTitle());
            statement.setFloat(2, updatedCource.getDuration());
            statement.setInt(3, updatedCource.getPrice());
            statement.setInt(4, courceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCource(int courceId) {
        try (Connection connection = DatabaseInitializer.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_COURSE)) {
            statement.setInt(1, courceId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
