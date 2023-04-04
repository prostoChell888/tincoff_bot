package ru.tinkoff.bot.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.tinkoff.bot.util.validation.ListOfIds;

import java.net.URI;
import java.util.List;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    @NotNull(message = "id should not be empty")
    @Min(value = 1, message = "id should be bigger than 0")
    Long id;
    @URL(message = "Invalid Url, Please provide a valid URL")
    @NotEmpty(message = "url should not be Empty")
    URI url;
    @NotEmpty(message = "description should not be Empty")
    String description;
    @ListOfIds
    List<Long> tgChatIds;
}
