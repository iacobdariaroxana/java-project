package com.laborator11.network.controllers;

import com.laborator11.network.services.RelationshipService;
import models.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertRelationship(@RequestParam int user1, @RequestParam String user2) {
        if (relationshipService.insertRelationship(user1, user2)) {
            return new ResponseEntity<>("Friendship created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Friendship cannot be created!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<Friend> getAllRelationships() {
        return relationshipService.getRelationships();
    }

    @GetMapping("/{user1}")
    public List<String> getRelationships(@PathVariable("user1") int user1) {
        return relationshipService.getUserRelationships(user1);
    }

    @GetMapping("/vip")
    public List<String> getVip(@RequestParam int k) {
        return relationshipService.getFamous(k);
    }
}