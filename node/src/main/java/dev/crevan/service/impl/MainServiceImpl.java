package dev.crevan.service.impl;

import dev.crevan.dao.RawDataDao;
import dev.crevan.entity.RawData;
import dev.crevan.service.MainService;
import dev.crevan.service.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MainServiceImpl implements MainService {

    private final RawDataDao rawDataDao;
    private final ProducerService producerService;

    public MainServiceImpl(final RawDataDao rawDataDao, final ProducerService producerService) {
        this.rawDataDao = rawDataDao;
        this.producerService = producerService;
    }

    @Override
    public void processTextMessage(final Update update) {
        saveRawData(update);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from Node");
        producerService.produceAnswer(sendMessage);
    }

    private void saveRawData(final Update update) {
        RawData rawData = RawData.builder()
                .update(update)
                .build();
        rawDataDao.save(rawData);
    }
}
