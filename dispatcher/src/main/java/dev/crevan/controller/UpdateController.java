package dev.crevan.controller;

import dev.crevan.service.UpdateProducer;
import dev.crevan.utills.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static dev.crevan.model.RabbitQueue.*;

@Log4j
@Component
public class UpdateController {

    private TelegramBot telegramBot;

    private final MessageUtils messageUtils;

    private final UpdateProducer updateProducer;



    public UpdateController(final MessageUtils messageUtils, final UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(final Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.getMessage() != null) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type received: " + update);
        }
    }

    public void setView(final SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void distributeMessagesByType(final Update update) {
        var message = update.getMessage();

        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setUnsupportedMessageTypeView(final Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения");
        setView(sendMessage);

    }

    private void setFileIsReceivedView(final Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Обрабатывается...");
        setView(sendMessage);
    }

    private void processPhotoMessage(final Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        setFileIsReceivedView(update);
    }

    private void processDocMessage(final Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
    }

    private void processTextMessage(final Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }

}
