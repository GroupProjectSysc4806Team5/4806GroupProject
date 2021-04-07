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
            Owner owner2 = new Owner("City of Ottawa Admin", "0774w4");
            ownerRepo.save(ownerTest);
            ownerRepo.save(owner2);

            User userTest = new User("User Test", "usertest@email.ca", "userTest");
            User user2 = new User("Some Guy", "someguy@email.ca", "S0M3oN3");
            userRepo.save(userTest);
            userRepo.save(user2);

            Bookstore storeTest = new Bookstore(ownerTest);
            storeTest.setName("Test Bookstore1");
            storeRepo.save(storeTest);

            Bookstore storeTestToo = new Bookstore(ownerTest);
            storeTestToo.setName("Test Bookstore2");
            storeRepo.save(storeTestToo);

            Bookstore OPL = new Bookstore(owner2);
            OPL.setName("OPL");
            storeRepo.save(OPL);

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

            // New Books and Stores based on real places

            Book theKeys = new Book();
            theKeys.setBookName("The Keys: A Memoir");
            theKeys.setAuthor("DJ Khaled");
            theKeys.setDescription("From Snapchat sensation, business mogul, and recording artist DJ Khaled, the book They don't want you to read reveals his major keys to success");
            theKeys.setISBN("0451497570");
            theKeys.setPublisher("Crown Archetype");
            theKeys.setPicture("Lion");
            theKeys.setPrice(24.00);
            theKeys.setQuantity(4);
            theKeys.setAvailable(true);
            theKeys.setStore(OPL);

            Book manga1 = new Book();
            manga1.setBookName("Fullmetal Alchemist: Fullmetal Edition, Vol. 9");
            manga1.setAuthor("Hiromu Arakawa");
            manga1.setDescription("Alchemy tore the Elric brothers’ bodies apart. Can their bond make them whole again?");
            manga1.setISBN("1421599902");
            manga1.setPublisher("VIZ Media LLC");
            manga1.setPicture("Envy");
            manga1.setPrice(26.72);
            manga1.setQuantity(5);
            manga1.setAvailable(true);
            manga1.setStore(storeTestToo);

            Book doWork = new Book();
            doWork.setBookName("How to Do the Work: Recognize Your Patterns, Heal from Your Past, and Create Your Self ");
            doWork.setAuthor("Dr. Nicole LePera");
            doWork.setDescription("From Dr. Nicole LePera, creator of \"the holistic psychologist\"—the online phenomenon with more than two million Instagram followers—comes a revolutionary approach to healing that harnesses the power of the self to produce lasting change.");
            doWork.setISBN("0063076810");
            doWork.setPublisher("Harper Wave");
            doWork.setPicture("Bold Title");
            doWork.setPrice(23.99);
            doWork.setQuantity(20);
            doWork.setAvailable(true);
            doWork.setStore(storeTestToo);

            Book lor = new Book();
            lor.setBookName("The Lord of the Rings Hardcover – Special Edition");
            lor.setAuthor("J.R.R. Tolkien");
            lor.setDescription("Sumptuous slipcased edition of Tolkien’s classic epic tale of adventure, fully illustrated in colour for the first time by the author himself.");
            lor.setISBN(" 0008471290");
            lor.setPublisher("HarperCollins");
            lor.setPicture("Sauron");
            lor.setPrice(149.99);
            lor.setQuantity(0);
            lor.setAvailable(false);
            lor.setStore(OPL);

            Book lone = new Book();
            lone.setBookName("Lone Survivor: The Eyewitness Account of Operation Redwing and the Lost Heroes of SEAL Team 10 ");
            lone.setAuthor("Marcus Luttrell");
            lone.setDescription("Follow along a Navy SEAL's firsthand account of American heroism during a secret military operation in Afghanistan in this true story of survival and difficult choices.");
            lone.setISBN("0316067601");
            lone.setPublisher("Back Bay Books");
            lone.setPicture("Trident");
            lone.setPrice(23.99);
            lone.setQuantity(10);
            lone.setAvailable(true);
            lone.setStore(OPL);

            Book f451 = new Book();
            f451.setBookName("Fahrenheit 451: A Novel");
            f451.setAuthor("Ray Bradbury");
            f451.setDescription("Guy Montag is a fireman. His job is to destroy the most illegal of commodities, the printed book, along with the houses in which they are hidden.");
            f451.setISBN("9781451673319");
            f451.setPublisher("Simon & Schuster");
            f451.setPicture("Book");
            f451.setPrice(23.00);
            f451.setQuantity(14);
            f451.setAvailable(true);
            f451.setStore(OPL);

            storeTest.addBook(bookTest);
            storeTest.addBook(bookTest2);
            storeTest.addBook(bookTest3);
            storeTestToo.addBook(manga1);
            storeTestToo.addBook(doWork);
            OPL.addBook(lor);
            OPL.addBook(theKeys);
            OPL.addBook(lone);
            OPL.addBook(f451);
            storeRepo.save(storeTest);
            storeRepo.save(OPL);
            storeRepo.save(storeTestToo);


        };
    }
}