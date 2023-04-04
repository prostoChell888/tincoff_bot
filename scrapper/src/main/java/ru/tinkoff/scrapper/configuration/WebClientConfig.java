package ru.tinkoff.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.clients.ClientFactory;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;

@Configuration
public class WebClientConfig {
    @Value("${url.gitHub:https://api.github.com/}")
    private  String gitHubUrl;

    @Value("${url.stackOverflow:https://api.stackexchange.com/2.3}")
    private  String stackOverflowUrl;

    @Bean
    GitHubClient gitHubClientClient() {
        return ClientFactory.createGitHubClient(gitHubUrl);
    }

    @Bean
    StackOverflowClient stackOverflowClientClient() {
        return ClientFactory.createStackOverflowClient(stackOverflowUrl);
    }







}
