package Bookstore;

import Bookstore.Model.Owner;
import Bookstore.repositories.OwnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebLauncher {
	
    public static void main(String[] args) {
    	
        SpringApplication.run(WebLauncher.class, args);
    }


    // This function will create a owner in the database for the login
    @Bean
    public CommandLineRunner createOwner(OwnerRepository repo){
        return (args) -> {
            repo.save(new Owner("Eugene Cain","pass"));
        };
    }
}