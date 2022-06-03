package com.laborator11.network.controllers;

import com.laborator11.network.services.MessageService;
import models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Message> messages = messageService.findByFromId(id);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages.toString(), HttpStatus.OK);
    }

    @GetMapping("/useroutbox/{id}")
    public ResponseEntity<String> getUserOutbox(@PathVariable("id") int id) {
        List<Message> messages = messageService.findByToId(id);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages.toString(), HttpStatus.OK);
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<String> getByHashtag(@PathVariable("hashtag") String hashtag) {
        List<Message> messages = messageService.findByHashtag(hashtag);
        if (messages == null) {
            return new ResponseEntity<>(
                    "Messages not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messages.toString(), HttpStatus.OK);
    }

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestParam int fromId,
                                                @RequestParam int toId,
                                                @RequestParam String message,
                                                @RequestParam int type) {
        messageService.createMessage(fromId, toId, message, type);
        return new ResponseEntity<>(
                "Message added successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        if (!messageService.deleteMessage(id)) {
            return new ResponseEntity<>("Message not found", HttpStatus.GONE);
        }
        return new ResponseEntity<>("Message removed", HttpStatus.OK);
    }
}
