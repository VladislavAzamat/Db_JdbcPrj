package aston_org.example.dbConnection;


import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            try {
                runLiquibase();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize liquibase!!!!!!!!!!", e);
            }
        }
        public static Connection getDatabaseConnection() throws SQLException {
            String url = "jdbc:postgresql://localhost:5433/aston_prj2";
            String user = "astonPrj_user";
            String password = "123";
            return DriverManager.getConnection(url, user, password);
        }
    public static void runLiquibase() {
        try {
            Connection connection = getDatabaseConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db.changelog/changelog.yaml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    }

