package com.laborator11.network.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@Controller
@RequestMapping("/")
@CrossOrigin("*")
public class StartController {
    @PermitAll
    @GetMapping
    public String getStartPage() {
        return "index";
    }

    @RequestMapping("/myAccount")
    public String getMyAccount() {
        return "myAccount";
    }

    @PostMapping("/users/register")
    public String test(){
        return "index";
    }

    @GetMapping("/public/{name}")
    public String getResource(@PathVariable("name") String file) {
        return "public/" + file;
    }
}
