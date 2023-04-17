package ru.tinkoff.parsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tinkoff.requasts.ParseResponse;

import static org.junit.jupiter.api.Assertions.*;

class StackOverFlowParserTest {

    @ParameterizedTest
    @CsvSource(value = {
            "https://stackoverflow.com/questions/26857487/cannot-access-the-classes-in-the-package, 26857487",
            "https://stackoverflow.com/questions/34571/how-do-i-test-a-class-that-has-private-methods-fields-or-inner-classes?rq=1, 34571",
            "https://stackoverflow.com/questions/3676664/unit-testing-of-private-methods?noredirect=1&lq=1, 3676664",
            "https://stackoverflow.com/questions/55611/javascript-private-methods?rq=1, 55611"
    })
    void parse_ValidUrls_WithOutHandler(String url, String res) {
        Parser parser = new StackOverFlowParser();

        ParseResponse response = parser.parse(url);

        assertEquals(res, response.getContentInStr());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "someWord",
            "https://github.com/pengrad/java-telegram-bot-api",
            "https://www.baeldung.com/java-nosuchmethod-error"})
    void parse_NotValid_WithOutHandler(String url) {
        Parser parser = new StackOverFlowParser();

        ParseResponse response = parser.parse(url);

        assertNull(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "someWord",
            "",
            "https://www.baeldung.com/java-nosuchmethod-error"})
    void parse_NotValid_WithHandler(String url) {
        Parser parser = new StackOverFlowParser();
        parser.setHandler(new GitHabParser());

        ParseResponse response = parser.parse(url);

        assertNull(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/pengrad/java-telegram-bot-api",
            "https://stackoverflow.com/questions/26857487/cannot-access-the-classes-in-the-package",
            "https://github.com/sanyarnd/tinkoff-java-course-2022/",
            "https://stackoverflow.com/questions/34571/how-do-i-test-a-class-that-has-private-methods-fields-or-inner-classes?rq=1",
            "https://stackoverflow.com/questions/3676664/unit-testing-of-private-methods?noredirect=1&lq=1",
            "https://stackoverflow.com/questions/55611/javascript-private-methods?rq=1"
    })
    void parse_Valid_WithHandler(String url) {
        Parser parser = new StackOverFlowParser();
        parser.setHandler(new GitHabParser());

        ParseResponse response = parser.parse(url);

        assertNotNull(response);
    }


}




