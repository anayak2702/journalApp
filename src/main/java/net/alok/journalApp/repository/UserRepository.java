package net.alok.journalApp.repository;


import net.alok.journalApp.entity.JournalEntry;
import net.alok.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// controller --> service --> repository
//  MongoRepository is already provided by spring data Mongodb that provides
// <pojo, data type of id>
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByuserName(String username);

}
