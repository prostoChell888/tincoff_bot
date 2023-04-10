package ru.tinkoff.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FirstTest extends IntegrationEnvironment {
    @SneakyThrows
    @Test
    public void someTestMethod() {
        Connection connection  = getConnection();

        assertNotNull(connection);


    }
}
