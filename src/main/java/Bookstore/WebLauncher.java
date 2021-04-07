package Bookstore;

import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.Model.Owner;
import Bookstore.Model.User;
import Bookstore.repositories.BookRepository;
import Bookstore.repositories.BookstoreRepository;
import Bookstore.repositories.OwnerRepository;
import Bookstore.repositories.UserRepository;
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
    public CommandLineRunner setup(
            OwnerRepository ownerRepo,
            UserRepository userRepo,
            BookstoreRepository storeRepo,
            BookRepository bookRepo) {
        return (args) -> {
            Owner ownerTest = new Owner("Eugene Cain", "pass");
            ownerRepo.save(ownerTest);

            User userTest = new User("User Test", "usertest@email.ca", "userTest");
            userRepo.save(userTest);

            Bookstore storeTest = new Bookstore(ownerTest);
            storeTest.setName("Test Bookstore1");
            storeRepo.save(storeTest);

            Bookstore storeTestToo = new Bookstore(ownerTest);
            storeTestToo.setName("Test Bookstore2");
            storeRepo.save(storeTestToo);

            Book bookTest = new Book();
            bookTest.setBookName("Test Book");
            bookTest.setAuthor("Test Author");
            bookTest.setDescription("Test Description");
            bookTest.setISBN("Test ISBN");
            bookTest.setPublisher("Test Publisher");
            bookTest.setPrice(20.00);
            bookTest.setAvailable(true);
            bookTest.setQuantity(1);
            bookTest.setStore(storeTest);

            Book bookTest2 = new Book();
            bookTest2.setBookName("Test Book 2");
            bookTest2.setAuthor("Test Author");
            bookTest2.setDescription("Test Description");
            bookTest2.setISBN("Test ISBN");
            bookTest2.setPublisher("Test Publisher");
            bookTest2.setPrice(25.00);
            bookTest2.setAvailable(true);
            bookTest2.setQuantity(15);
            bookTest2.setStore(storeTest);

            Book bookTest3 = new Book();
            bookTest3.setBookName("Test Book 3");
            bookTest3.setAuthor("Test Author");
            bookTest3.setDescription("Test Description");
            bookTest3.setISBN("Test ISBN");
            bookTest3.setPublisher("Test Publisher");
            bookTest3.setPrice(40.00);
            bookTest3.setAvailable(true);
            bookTest3.setQuantity(4);
            bookTest3.setStore(storeTest);

            storeTest.addBook(bookTest);
            storeTest.addBook(bookTest2);
            storeTest.addBook(bookTest3);
            storeRepo.save(storeTest);
        };
    }
}