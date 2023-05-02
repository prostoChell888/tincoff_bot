package ru.tinkoff;

import ru.tinkoff.parsers.GitHabParser;
import ru.tinkoff.parsers.Parser;
import ru.tinkoff.parsers.StackOverFlowParser;
import ru.tinkoff.requasts.GitHabParseResponse;
import ru.tinkoff.requasts.ParseResponse;
import ru.tinkoff.requasts.StackOverflowParseResponse;

public class Example {
    public static void main(String[] args) {
        withPattern();
        withOutPattern();
    }

    public static void withPattern() {
        Parser parser = new GitHabParser();
        parser.setHandler(new StackOverFlowParser());

        ParseResponse response1 = parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        ParseResponse response2 = parser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        ParseResponse response3 = parser.parse("https://stackoverflow.com/search?q=unsupported%20link");

        System.out.println(response1);
        System.out.println(response2);
        System.out.println(response3);
    }

    public static void withOutPattern() {
        GitHabParser gitHabParser = new GitHabParser();

        GitHabParseResponse response1_1 = (GitHabParseResponse) gitHabParser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        GitHabParseResponse response1_2 = (GitHabParseResponse) gitHabParser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        System.out.println(response1_1);
        System.out.println(response1_2);


        StackOverFlowParser stackOverFlowParser = new StackOverFlowParser();

        StackOverflowParseResponse response2_1 = (StackOverflowParseResponse) stackOverFlowParser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        StackOverflowParseResponse response2_2 = (StackOverflowParseResponse) stackOverFlowParser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        System.out.println(response2_1);
        System.out.println(response2_2);
    }
}
