package ru.tinkoff.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;

import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {
    protected static final PostgreSQLContainer<?> PSQL_CONTAINER;

    static {
        PSQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        PSQL_CONTAINER.start();
        try {
            Path path = new File("../").toPath().toAbsolutePath().normalize();
            Connection connection = getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("migrations/master.xml", new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    protected static Connection getConnection() throws SQLException {
        String url = PSQL_CONTAINER.getJdbcUrl();
        String password =  PSQL_CONTAINER.getPassword();
        String user = PSQL_CONTAINER.getUsername();

        return DriverManager.getConnection(url, user, password);
    }
}
