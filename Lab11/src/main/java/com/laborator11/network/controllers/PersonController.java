package com.laborator11.network.controllers;


import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.laborator11.network.services.UserService;

import java.util.List;
@Controller
@RequestMapping("/users")
@CrossOrigin("*")
public class PersonController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable("id") int id) {
        User user = userService.findUser(id);
        if (user == null) {
            return new ResponseEntity<>(
                    "User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }

    @GetMapping
    public List<User> getPersons() {
        return userService.getUsers();
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> createUserObject(@RequestBody User user) {
        String response = userService.createUserObject(user);
        if(response.equals("success")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable int id, @RequestParam String name) {
        if (!userService.updateUser(id, name)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable int id) {
        if (!userService.deleteUser(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.GONE);
        }
        return new ResponseEntity<>("User removed", HttpStatus.OK);
    }
}
