package ru.tinkoff.scrapper.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public  class ClientFactory {
    private static final String gitHubDefaultUrl = "https://api.github.com/";

    private static final String stackOverflowDefaultUrl = "https://api.stackexchange.com/2.3";

    private static HttpServiceProxyFactory getHttpServiceProxyFactory(String url) {
        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .build();

        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
    }

    public static GitHubClient createGitHubClient(String url) {
          return getHttpServiceProxyFactory(url).createClient(GitHubClient.class);
    }

    public static GitHubClient createGitHubClient() {
        return getHttpServiceProxyFactory(gitHubDefaultUrl).createClient(GitHubClient.class);
    }

    public static StackOverflowClient createStackOverflowClient(String url) {
        return getHttpServiceProxyFactory(url).createClient(StackOverflowClient.class);
    }

    public static StackOverflowClient createStackOverflowClient() {
        return getHttpServiceProxyFactory(stackOverflowDefaultUrl).createClient(StackOverflowClient.class);
    }


}
