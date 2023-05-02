package ru.tinkoff.bot.tg.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.tinkoff.bot.tg.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StateTest {

    @Mock
    User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("states")
    void listCommand_UserWithTrackLinks(State state) throws MalformedURLException {
        when(user.getLinkSet()).thenReturn(List.of(
                new URL("https://habr.com"),
                new URL("https://mvnrepository.com"),
                new URL("https://mail.google.com")));

        String response = state.listCommand(user);

        String expectedResponse = """
                Отслеживаемые ссылки:
                https://habr.com
                https://mvnrepository.com
                https://mail.google.com
                """;
        assertEquals(expectedResponse, response);
    }


    @ParameterizedTest
    @MethodSource("states")
    void listCommand_UserWithoutTrackLinks(State state) {
        when(user.getLinkSet()).thenReturn(new ArrayList<>());

        String response = state.listCommand(user);

        String expectedResponse = "Отслеживаемые ссылки отсутсвуют";
        assertEquals(expectedResponse, response);
    }

    @ParameterizedTest
    @MethodSource("states")
    void listCommand_UserStateIsDefault() {
        User userWithState = new User(1L);
        userWithState.setState(TypeOfState.DEFAULT);

        State state = new DefaultState();
        state.listCommand(userWithState);

        assertEquals( TypeOfState.DEFAULT, userWithState.getState());
    }

    private static Stream<Arguments> states() {
        return Stream.of(
                Arguments.of(new DefaultState()),
                Arguments.of(new AddLinkState()),
                Arguments.of(new DelLinkState())
        );
    }
}