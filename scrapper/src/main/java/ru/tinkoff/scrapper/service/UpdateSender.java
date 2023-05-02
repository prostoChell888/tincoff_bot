package ru.tinkoff.scrapper.service;

import ru.tinkoff.scrapper.dto.request.LinkUpdateRequest;

public interface UpdateSender {
    void send(LinkUpdateRequest update);
}
