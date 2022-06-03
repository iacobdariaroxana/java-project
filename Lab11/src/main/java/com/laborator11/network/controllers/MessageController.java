package com.laborator11.network.controllers;

import com.laborator11.network.services.MessageService;
import models.Message;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getMessage(@PathVariable("id") int id) {
        Message message = messageService.findMessage(id);
        if (message == null) {
            return new ResponseEntity<>(
                    "Message not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message.toString(), HttpStatus.OK);
    }

    @GetMapping("/userinbox/{id}")
    public ResponseEntity<String> getUserInbox(@PathVariable("id") int id) {
        List<Message> messages = messageService.findByToId(id);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        UserRepository userRepository = new UserRepository("User");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Message sms : messages) {
            User user = userRepository.findById(sms.getFromId());
            stringBuilder.append("{\"from\": \"");
            stringBuilder.append(user.getUsername()).append("\", ");
            stringBuilder.append("\"message\": \"").append(sms.getMessage()).append("\"}");
            i++;
            if (i != messages.size()) stringBuilder.append("###");
        }
        return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.OK);
    }

    @GetMapping("/useroutbox/{id}")
    public ResponseEntity<String> getUserOutbox(@PathVariable("id") int id) {
        List<Message> messages = messageService.findByFromId(id);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        UserRepository userRepository = new UserRepository("User");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Message sms : messages) {
            User user = userRepository.findById(sms.getToId());
            stringBuilder.append("{\"to\": \"");
            stringBuilder.append(user.getUsername()).append("\", ");
            stringBuilder.append("\"message\": \"").append(sms.getMessage()).append("\"}");
            i++;
            if (i != messages.size()) stringBuilder.append("###");
        }
        return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.OK);
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<String> getByHashtag(@PathVariable("hashtag") String hashtag,
                                               @RequestParam int id) {
        System.err.println(hashtag + " " + id);
        List<Message> messages = messageService.findByHashtag(hashtag, id);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        UserRepository userRepository = new UserRepository("User");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Message sms : messages) {
            User user = userRepository.findById(sms.getFromId());
            stringBuilder.append("{\"author\": \"");
            stringBuilder.append(user.getUsername()).append("\", ");
            stringBuilder.append("\"message\": \"").append(sms.getMessage()).append("\"}");
            i++;
            if (i != messages.size()) stringBuilder.append("###");
        }
        System.err.println(stringBuilder);
        return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.OK);
    }

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestParam String fromUsername,
                                                @RequestParam String toUsername,
                                                @RequestParam String message,
                                                @RequestParam int type) {
        String responseMessage = messageService.createMessage(fromUsername, toUsername, message, type);
        return new ResponseEntity<>(
                responseMessage, HttpStatus.CREATED);
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createMultipleMessages(@RequestParam String fromUsername,
                                                @RequestParam String toUsername,
                                                @RequestParam String message,
                                                @RequestParam int type) {
        messageService.createMessageMultiple(fromUsername, toUsername, message, type);
        return new ResponseEntity<>(
                "Messages sent successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        if (!messageService.deleteMessage(id)) {
            return new ResponseEntity<>("Message not found", HttpStatus.GONE);
        }
        return new ResponseEntity<>("Message removed", HttpStatus.OK);
    }
}
