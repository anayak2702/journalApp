package net.alok.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		System.out.println(context.getEnvironment().getActiveProfiles()[0]);


	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}

/*@EnableTransactionManagement - Tells spring find all methods that has @Transactional and for those methods
  will create transactional context, meaning will create a container that will contain all the DB operations
  of that method and will be treated as one operation. So we are achieving atomicity.
  If two users access the API simultaneously, then two containers will be created one per user.

  The rollback is done by interface PlatformTransactionManager and the interface is implemented by
  MongoTransactionManager, so we would need to create a bean to implement this.
  For this we will create a method returning PlatformTransactionManager , but the actual implementation is
  done by MongoTransactionManager, so we need to return an instance of MongoTransactionManager.

  MongoDatabaseFactory helps in establishing connection with database. Whatever database operations we are doing,
  we are using instance of MongoDatabaseFactory

  MongoDatabaseFactory dbFactory is passed by spring based on the connection we have done in application properties.
  SimpleMongoClientDatabaseFactory implements it.
 */
