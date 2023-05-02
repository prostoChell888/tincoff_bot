package ru.tinkoff.scrapper.dto.responce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DTOGitHubEventResponse {
    @JsonProperty("type")
    String type;
    @JsonProperty("created_at")
    OffsetDateTime created_at;
}
