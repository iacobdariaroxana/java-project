package com.laborator11.network.services;

import models.Message;
import models.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.AbstractRepository;
import repositories.MessageRepository;
import repositories.UserRepository;

import java.util.List;

@Component
@Service
public class MessageService {

    private MessageRepository dataMessage = new MessageRepository("Message");
    private AbstractRepository<User, Integer> dataUser = new UserRepository("User");

    public Message findMessage(int id) {
        return dataMessage.findById(id).get(0);
    }

    public List<Message> findByHashtag(String hashtag, int id) {
        return dataMessage.findByHashtag(hashtag, id);
    }

    public List<Message> getMessages() {
        return dataMessage.findAll();
    }

    public String createMessage(String fromUsername, String toUsername, String message, int type) {
        if (dataUser.findByUsername(fromUsername).isEmpty() || dataUser.findByUsername(toUsername).isEmpty()) {
            System.out.println("sender or receiver doesn't exist" + type);
            return "User doesn't exist!";
        }
        int fromId = dataUser.findByUsername(fromUsername).get(0).getId();
        int toId = dataUser.findByUsername(toUsername).get(0).getId();
        dataMessage.create(new Message(fromId, toId, message, type));
        System.out.println("sending message to ... " + toUsername);
        return "Message sent successfully!";
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

    public void createMessageMultiple(String fromUsername, String toFriendsUsernames, String message, int type) {
        if (dataUser.findByUsername(fromUsername).isEmpty()) {
            System.out.println("sender user doesn't exist");
            return;
        }
        int fromId = dataUser.findByUsername(fromUsername).get(0).getId();
        String[] friends = toFriendsUsernames.split(" ");
        int toId;
        for (String friend : friends) {
            if (dataUser.findByUsername(friend).isEmpty())
                continue;
            toId = dataUser.findByUsername(friend).get(0).getId();
            dataMessage.create(new Message(fromId, toId, message, type));
            System.out.println("sending message to ... " + friend);
        }
    }
}

