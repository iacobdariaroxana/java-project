package com.laborator11.network.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String test() {
        return "index";
    }

    @GetMapping("/public/{name}")
    public String getResource(@PathVariable("name") String file) {
        return "public/" + file;
    }

    @GetMapping("/myLogout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            System.out.println("COOKIE " + cookie.getName());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return new ResponseEntity<>("logout successful", HttpStatus.OK);
    }
}
