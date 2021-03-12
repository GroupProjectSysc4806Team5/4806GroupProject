package Bookstore.Controllers;

import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.Model.Owner;
import Bookstore.Model.User;
import Bookstore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private BookRepository bookRepo;
    private BookstoreRepository bookstoreRepo;
    private CartRepository cartRepo;
    private UserRepository userRepo;
    private OwnerRepository ownerRepo;

    public WebController(BookRepository bookRepo, BookstoreRepository bookstoreRepo, CartRepository cartRepo, UserRepository userRepo, OwnerRepository ownerRepo){
        this.bookRepo = bookRepo;
        this.ownerRepo = ownerRepo;
        this.bookstoreRepo = bookstoreRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/new-owner")
    public String newOwner(Model model){

        model.addAttribute(new Owner());
        return "new_owner";
    }

    @PostMapping("/new-owner-successful")
    public String newOwnerSuccess(Model model, @ModelAttribute Owner owner){
        ownerRepo.save(owner);
        return "new_owner_successful";
    }

    @GetMapping("/new-book")
    public String newBook(Model model) {
        model.addAttribute(new Book());
        return "new_book";

    }

    @PostMapping("/new-book-successful")
    public String newBookSuccess(Model model, @ModelAttribute Book book){
        bookRepo.save(book);
        return "new_book_successful";
    }

    @GetMapping("new-bookstore")
    public String newBookstore(Model model) {
        model.addAttribute(new Bookstore());
        return "new_bookstore";
    }

    @PostMapping("new-bookstore-successful")
    public String newBookstoreSuccess(Model model, @ModelAttribute Bookstore bookstore) {
        bookstoreRepo.save(bookstore);
        return "new_book_successful";
    }

    @GetMapping("new-user")
    public String newUser(Model model){
        model.addAttribute(new User());
        return "new_user";
    }

    @PostMapping("new-user-successful")
    public String newUserSuccess(Model model, @ModelAttribute User user){
        userRepo.save(user);
        return "new_user_successful";
    }




}
