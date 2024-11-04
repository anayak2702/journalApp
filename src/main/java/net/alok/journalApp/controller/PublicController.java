package net.alok.journalApp.controller;

import net.alok.journalApp.entity.User;
import net.alok.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthcheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
//        userService.saveEntry(user);
        userService.saveNewUser(user);

    }
}
