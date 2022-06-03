package com.laborator11.network.services;

import models.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.MessageRepository;
import java.util.List;

@Component
@Service
public class MessageService {

    private MessageRepository dataMessage = new MessageRepository("Message");

    public Message findMessage(int id) {
        return dataMessage.findById(id).get(0);
    }

    public List<Message> findByHashtag(String hashtag) {
        return dataMessage.findByHashtag(hashtag);
    }

    public List<Message> getMessages() {
        return dataMessage.findAll();
    }

    public void createMessage(int fromId, int toId, String message, int type) {
        dataMessage.create(new Message(fromId, toId, message, type));
    }

    public void createMessageObject(Message message) {
        dataMessage.create(message);
    }

    public boolean updateMessage(int id, String updatedMessage) {
        Message message = dataMessage.findById(id).get(0);
        if (message == null) {
            return false;
        }
        dataMessage.updateMessage(message.getId(), updatedMessage);
        message.setMessage(updatedMessage);
        return true;
    }

    public boolean deleteMessage(int id) {
        Message message = dataMessage.findById(id).get(0);
        if (message == null) {
            return false;
        }
        dataMessage.delete(id);
        return true;
    }

    public List<Message> findByFromId(int id) {
        return dataMessage.findByFromId(id);
    }

    public List<Message> findByToId(int id) {
        return dataMessage.findByToId(id);
    }
}

