package ru.tinkoff.scrapper.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.types.NotFoundException;
import ru.tinkoff.scrapper.clients.GitHubClient;
import ru.tinkoff.scrapper.dto.request.GitGubRepRequest;
import ru.tinkoff.scrapper.dto.responce.GitHubResponse;

@Service
@RequiredArgsConstructor
@Validated
public class GitHubService {
    private final GitHubClient gitHubClient;

    public ResponseEntity<GitHubResponse> getRepInfo(@Valid GitGubRepRequest request) {
        var response =  gitHubClient.getRepo(request.owner(), request.repo()).blockFirst();
        if (response == null ){
            throw new NotFoundException("Репозиторий не найден");
        }

        return ResponseEntity.ok().body(response);
    }
}
