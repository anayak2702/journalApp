package net.alok.journalApp.controller;


import net.alok.journalApp.entity.JournalEntry;
import net.alok.journalApp.entity.User;
import net.alok.journalApp.repository.UserRepository;
import net.alok.journalApp.service.JournalEntryService;
import net.alok.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }



//    @PutMapping("/{userName}")
    @PutMapping
//    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName)
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userinDb = userService.findByUserName(userName);
        userinDb.setUserName(user.getUserName());
        userinDb.setPassword(user.getPassword());
        userService.saveNewUser(userinDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }


    @DeleteMapping
    public ResponseEntity<?> deleteById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByuserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }

}