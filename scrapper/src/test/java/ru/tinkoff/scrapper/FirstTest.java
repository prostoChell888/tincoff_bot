package ru.tinkoff.scrapper;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;


import static org.junit.Assert.assertNotNull;


@SpringBootTest
public class FirstTest extends IntegrationEnvironment {

//    @Autowired
//    LinkRepository q ;
//
//    @SneakyThrows
//    @Test
//    public void contextLoads() {
//        var num = q.getAllLinks();
//        assertNotNull(num);
//
//    }


    @SneakyThrows
    @Test
    public void someTestMethod() {
        Connection connection  = getConnection();

        assertNotNull(connection);


    }
}
