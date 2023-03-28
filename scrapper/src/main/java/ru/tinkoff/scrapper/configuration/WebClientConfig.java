package ru.tinkoff.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.clients.ClientFactory;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.clients.StackOverflowClient;

@Configuration
public class WebClientConfig {
    @Value("${url.gitHub}")
    private  String gitHubUrl;

    @Value("${url.stackOverflow}")
    private  String stackOverflowUrl;

    @Bean
    GitHubClient gitHubClientClient() {
        return ClientFactory.createGitHubClient(gitHubUrl);
//        return ClientFactory.createGitHubClient();
    }

    @Bean
    StackOverflowClient stackOverflowClientClient() {
        return ClientFactory.createStackOverflowClient(stackOverflowUrl);
//        return ClientFactory.createStackOverflowClient();
    }

    @Bean
    public Long interval2(){
        return 5000L;
    }





}
