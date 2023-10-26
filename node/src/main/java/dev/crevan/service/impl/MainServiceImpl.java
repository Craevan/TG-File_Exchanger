package dev.crevan.service.impl;

import dev.crevan.dao.AppUserDao;
import dev.crevan.dao.RawDataDao;
import dev.crevan.entity.AppUser;
import dev.crevan.entity.RawData;
import dev.crevan.entity.enums.UserState;
import dev.crevan.service.MainService;
import dev.crevan.service.ProducerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class MainServiceImpl implements MainService {

    private final RawDataDao rawDataDao;
    private final ProducerService producerService;
    private final AppUserDao appUserDao;

    public MainServiceImpl(final RawDataDao rawDataDao, final ProducerService producerService, final AppUserDao appUserDao) {
        this.rawDataDao = rawDataDao;
        this.producerService = producerService;
        this.appUserDao = appUserDao;
    }

    private void saveRawData(final Update update) {
        RawData rawData = RawData.builder()
                .update(update)
                .build();
        rawDataDao.save(rawData);
    }

    private AppUser findOrSaveAppUser(final User telegramUser) {
        AppUser persistentAppUser = appUserDao.findAppUserByTelegramId(telegramUser.getId());
        if (persistentAppUser == null) {
            AppUser transientAppUser = AppUser.builder()
                    .telegramId(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //TODO изменить значение по умолчанию после добавления регистрации
                    .isActive(true)
                    .userState(UserState.BASIC_STATE)
                    .build();

            return appUserDao.save(transientAppUser);
        }

        return persistentAppUser;
    }

    @Override
    public void processTextMessage(final Update update) {
        saveRawData(update);

        var textMessage = update.getMessage();
        var telegramUSer = textMessage.getFrom();
        var appUser = findOrSaveAppUser(telegramUSer);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Hello from Node");
        producerService.produceAnswer(sendMessage);
    }
}
