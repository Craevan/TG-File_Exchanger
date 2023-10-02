package dev.crevan.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumer {

    void consume(final SendMessage sendMessage);
}
