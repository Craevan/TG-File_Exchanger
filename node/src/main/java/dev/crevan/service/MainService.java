package dev.crevan.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainService {

    void processTextMessage(final Update update);
}
