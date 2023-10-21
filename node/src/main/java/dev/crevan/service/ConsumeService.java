package dev.crevan.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumeService {

    void consumeTextMessageUpdates(final Update update);
    void consumeDocMessageUpdates(final Update update);
    void consumePhotoMessageUpdates(final Update update);
}
