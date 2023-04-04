package ru.tinkoff.parsers;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tinkoff.requasts.GitHabResponse;
import ru.tinkoff.requasts.ParseResponse;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GitHabParserTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "someWord",
            "https://stackoverflow.com/questions/55611/javascript-private-methods?rq=1",
            "https://www.baeldung.com/java-nosuchmethod-error"})
    void parse_NotValid_WithOutHandler(String url) {
        Parser parser = new GitHabParser();

        ParseResponse response = parser.parse(url);

        assertNull(response);
    }

    @ParameterizedTest
    @MethodSource("giHubResponses")
    void parse_ValidUrls_WithOutHandler(String url, GitHabResponse expectedRes) {
        var parser = new GitHabParser();

        ParseResponse response = parser.parse(url);

        assertEquals( expectedRes, response);
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
        Parser parser = new GitHabParser();
        parser.setHandler(new StackOverFlowParser());

        ParseResponse response = parser.parse(url);

        assertNotNull(response);
    }

    private static Stream<Arguments> giHubResponses() {
        return Stream.of(
                Arguments.of("https://github.com/petevg/Markdown-Edit",
                        new GitHabResponse("petevg", "Markdown-Edit")),
                Arguments.of("https://github.com/hackiftekhar/IQKeyboardManager",
                        new GitHabResponse("hackiftekhar", "IQKeyboardManager")),
                Arguments.of("https://github.com/cocos2d/cocos2d-x",
                        new GitHabResponse("cocos2d", "cocos2d-x"))
        );
    }
}