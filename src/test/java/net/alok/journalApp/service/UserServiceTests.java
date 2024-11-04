package net.alok.journalApp.service;

import net.alok.journalApp.entity.User;
import net.alok.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //Will start the Spring Context so that the beans will be created.
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ValueSource(strings =  {
            "ram",
            "alok",
            "aayansh",
            "shyam"
    })
    public void testFindByUserName(String name){
        assertNotNull(userRepository.findByUserName(name),"failed for: " + name);
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @Test
    public void testFindJournal(){
        User user = userRepository.findByUserName("ram");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }
}
