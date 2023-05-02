package ru.tinkoff.scrapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.scrapper.service.client.Q;

@SpringBootTest
class ScraperApplicationTests extends IntegrationEnvironment {

    @Autowired
    Q q;
    @Test
    public void contextLoads() {

        q.foo();

    }

}
