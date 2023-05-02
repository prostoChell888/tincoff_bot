package ru.tinkoff.scrapper.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.scrapper.dto.responce.DTOGitHubEventResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOGitHubEventResponses {


        private DTOGitHubEventResponse[] events;

}
