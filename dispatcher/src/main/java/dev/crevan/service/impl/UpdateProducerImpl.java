package dev.crevan.service.impl;

import dev.crevan.service.UpdateProducer;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@Service
public class UpdateProducerImpl implements UpdateProducer {

    @Override
    public void produce(final String rabbitQueue, final Update update) {
        log.debug(update.getMessage().getText());
        //TODO
    }
}
