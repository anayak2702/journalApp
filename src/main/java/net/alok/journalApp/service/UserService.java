package net.alok.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.alok.journalApp.entity.User;
import net.alok.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    //Dependency Injection
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //we want only one instance of logger would be created per each instance of JournalEntryService.Each logger is associated
    //with a specific class.Slf4j is an abstraction on logback. So we would use logback framework through Slf4j.Through slf4j
    //we can interact with the underlying implementation which is in this case logback.
    //we can use @slf4j instead

    public void saveEntry(User user){

        userRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e){
//            logger.info("Exception in saving new user : ",e);
//            logger.warn("Exception in saving new user");
//            logger.error("Error occurred for {} : ",user.getUserName(),e);
            log.error("Error occurred for {} : ",user.getUserName());
            log.warn("Error occurred for {} : ",user.getUserName());
            log.info("Error occurred for {} : ",user.getUserName());
            log.debug("Error occurred for {} : ",user.getUserName());
//            logger.debug("Exception in saving new user");
//            logger.trace("Exception in saving new user");
            return false;
        }

    }

    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

//    public List<User> getAll(){
//
//        return userRepository.findAll();
//    }

    public Optional<User> findById(ObjectId id){

        return userRepository.findById(id);
    }

    public void deletebyId(ObjectId id){

        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> getAll() {

        return userRepository.findAll();
    }
}

//JournalEntryRepository is not a bean, but spring will create an implementation of the interface in runtime and it will get
// injected here.

// controller --> service --> repository


