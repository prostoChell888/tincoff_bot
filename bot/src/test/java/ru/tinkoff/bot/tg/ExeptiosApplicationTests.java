package ru.tinkoff.bot.tg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.bot.Q;


@SpringBootTest
class ExeptiosApplicationTests {

    @Autowired
    private Q linksService;

    @Test
    void contextLoads() {

        var w = linksService.foo();



    }
}