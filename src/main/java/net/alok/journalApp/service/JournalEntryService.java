package net.alok.journalApp.service;

import net.alok.journalApp.entity.JournalEntry;
import net.alok.journalApp.entity.User;
import net.alok.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    //Dependency Injection
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
//            user.setUserName(null);
            userService.saveEntry(user);

        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error has occured while saving the entry.",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){

      journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deletebyId(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);

            }

        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry. ", e);
        }
        return removed;

    }



}

//JournalEntryRepository is not a bean, but spring will create an implementation of the interface in runtime and it will get
// injected here.

// controller --> service --> repository

//spring security
//authorization - whether you have access to the API (e.g MongoDb server) (i.e Read access, write access etc.)
//authentication - if you can access, then do you have the right credentials to log in
//               - The process of verifying a user's identity (e.g. username and password)
//authorization - The process of granting or denying access to specific resources or actions based on the
//                authenticated user's roles and permissions.
// Once the dependency is added, Spring Boot's auto-configuration feature will automatically apply security
// to the application.
// By default, Spring Security uses HTTP Basic authentication.

// The client sends an authorization header Authorization: Basic<encoded-string>
// The server decodes the string, extracts the username and password, and verifies them. if they're correct,
// access is granted. Otherwise, an "Unauthorized" response is sent back.

// Encoding - Credentials are combined into a string like username:password which is then encoded using Base64
// By default, all endpoints will be secured. Spring Security will generate a default user with a random password
// that is printed in the console logs on startup.

// you can also configure a username & password in your application.properties
// spring.security.user.name=user
// spring.security.user.password=password

// @EnableWebSecurity - This annotation signals Spring to enable its web security support. This is what makes
// your application secured. It's used in conjuction with @Configuration.
// We added the dependency in pom.xml, but it says we are going to customize the security configuration.

// WebSecurityConfigurerAdapter is a utility class in the Spring Security framework that provides the default
// configurations and allows customization of certain features. By extending it, you can configure and customize
// Spring Security for your application needs.

// Basic Authentication, by its design is stateless.(Meaning the second request doesn't know what happened in the
// first request.

/*
   Some applications do mix Basic Authentication with session management for various reasons.This isn't standard
   behaviour and requires additional setup and logic. In such scenarios, once the user's credentials are verified
   via Basic Authentication, a session might be established, and the client is provided a session cookie. This way,
   the client won't need to send the Authorization header with every request, and the server can rely on the session
   cookie to identify the authenticated user.

   When you log in with Spring Security, it manages your authentication across multiple requests, despite HTTP being
   stateless.

   1. Session Creation: After successful authentication, an HTTP session is formed. Your authentication details
      are stored in this session.
   2. Session Cookie: A JSESSIONID cookie is sent to your browser, which gets sent back with subsequent requests,
      helping the server recognize your session.
   3. SecurityContext: Using the JSESSIONID, Spring Security fetches your authentication details for each request.
   4. Session Timeout: Sessions have a limited life. If you're inactive past this limit, you're logged out.
   5. Logout: When logging out, your session ends, and the related cookie is removed.
   6. Remember-Me: Spring Security can remember you even after the session ends using a different persistent cookie
      (typically have a longer lifespan)

   In essence, Spring Security leverages sessions and cookies, mainly JSESSIONID, to ensure you remain authenticated
   across requests.

   We want our Spring Boot application to authenticate users based on their credentials stored in MongoDB database.
   This means that our users and their passwords(hashed) will be stored in MongoDB, and when user tries to login, the
   system should check the provided credentials against what's stored in the database.

   A. A User entity to represent the user data model.
   B. A repository UserRepository to interact with MongoDB.
   C. UserDetailsService implementation to fetch user details.
   D. A configuration SecurityConfig to integrate everything with Spring Security.

   Spring provides interface UserDetailsService that has method loadUserByUsername. So we have to make a class that
   will implement UserDetailsService

 */

