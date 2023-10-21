package dev.crevan.service.impl;

import dev.crevan.service.ConsumeService;
import dev.crevan.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.crevan.model.RabbitQueue.*;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumeService {

    private final MainService mainService;

    public ConsumerServiceImpl(final MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(final Update update) {
        log.debug("NODE: Text message received");
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(final Update update) {
        log.debug("NODE: Doc message received");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(final Update update) {
        log.debug("NODE: Photo message received");
    }
}
