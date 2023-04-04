package ru.tinkoff.scrapper.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tincoff.exeptios.types.NotFoundException;
import ru.tinkoff.scrapper.clients.StackOverflowClient;
import ru.tinkoff.scrapper.dto.responce.stackOverflowe.StackOverFlowResponse;
import ru.tinkoff.scrapper.util.validation.anotations.Id;

@Service
@RequiredArgsConstructor
@Validated
public class StackOverFlowService {
    private final StackOverflowClient stackOverflowClient;

    public ResponseEntity<StackOverFlowResponse> getQuestionInfo(@Id Long id) {
       var listResponse =  stackOverflowClient.getAnswerInfoById(id).blockFirst();
        if (listResponse == null || listResponse.items().length == 0){
            throw new NotFoundException("Вопрос с данным id не найден");
        }

        return ResponseEntity.ok().body(listResponse.items()[0]);
    }
}
