package ru.tinkoff.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.parsers.GitHabParser;
import ru.tinkoff.parsers.Parser;
import ru.tinkoff.parsers.StackOverFlowParser;
import ru.tinkoff.scrapper.clients.BotClient;
import ru.tinkoff.scrapper.clients.ClientFactory;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;

@Configuration
public class WebClientConfig {
    @Value("${url.gitHub}")
    private  String gitHubUrl;

    @Value("${url.stackOverflow}")
    private  String stackOverflowUrl;

    @Value("${url.bot}")
    private  String botUrl;

    @Bean
    GitHubClient gitHubClient() {
        return ClientFactory.createGitHubClient(gitHubUrl);
//        return ClientFactory.createGitHubClient();
    }

    @Bean
    StackOverflowClient stackOverflowClient() {
        return ClientFactory.createStackOverflowClient(stackOverflowUrl);
//        return ClientFactory.createStackOverflowClient();
    }

    @Bean
    BotClient botClient() {
        return ClientFactory.createBotClient(botUrl);
//        return ClientFactory.createStackOverflowClient();
    }

    @Bean
    Parser parser() {
        Parser parser = new GitHabParser();
        parser.setHandler(new StackOverFlowParser());

        return parser;
    }

    @Bean
    public Long interval2(){
        return 5000L;
    }





}
