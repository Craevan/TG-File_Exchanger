package dev.crevan.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerService {

    void produceAnswer(final SendMessage sendMessage);
}
