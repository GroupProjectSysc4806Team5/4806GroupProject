package Bookstore.Controller;

import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.Model.Owner;
import Bookstore.Model.User;
import Bookstore.repositories.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("add-book")
    public String addBook(Model model, @RequestParam(required = false, name = "id") String id){

        User u = userRepo.findById(Long.parseLong(id));

        if(u == null){
            return "add_book_error";
        }

        Iterable<Book> books = bookRepo.findAll();
        ArrayList<Book> book_list = new ArrayList<>();
        Iterator i =  books.iterator();

        while(i.hasNext()){
            Book b = (Book) i.next();
            book_list.add(b);
        }

        // The book_id of the book to be added to a User's Cart
        String book_id = "";

        model.addAttribute("user",u);
        model.addAttribute("list", book_list);
        model.addAttribute("id",id);
        model.addAttribute("book_id", book_id);
        return "add_book";

    }

    @PostMapping("add-book-successful")
    public String addBookSuccess(Model model, @RequestParam(required = false, name = "id") String id, @ModelAttribute String book_id, @ModelAttribute User user) {
        Book b = bookRepo.findById(Long.parseLong(book_id));
        if(b == null){
            return "error";
        }

        user.cart.addBook(b);


        model.addAttribute("cart", user.getCart());

        return "add_book_successful";

    }

    // Temporary Mapping, this will replace the main page with a login
    @GetMapping("login")
    public String ownerLogin(Model model) {

        model.addAttribute("user",new User());
        return "login";
    }

    // Temporary Post Mapping
    @PostMapping("login-check")
    public String checkLogin(Model model, @ModelAttribute User user){

        List<Owner> possible_owners = ownerRepo.findByName(user.getName());
        List<User> possible_users = userRepo.findByName(user.getName());

        // Check the owners if the list is not empty
        if(!possible_owners.isEmpty()) {
            for (Owner o : possible_owners) {
                System.out.println("========================================");
                System.out.println("Provided info: " + user.getName() + "\nPassword: " + user.getPassword());
                System.out.println("possible_owners info: " + o.getName() + "\nPassword: " + o.getPassword());

                if (o.getName().equals(user.getName()) && o.getPassword().equals(user.getPassword())) {
                    model.addAttribute("name", o.getName());
                    return "login_success";
                }
            }

            return "login_fail";
        } else if (!possible_users.isEmpty()) {
            for (User u: possible_users) {
                System.out.println("========================================");
                System.out.println("Provided info: " + user.getName() + "\nPassword: " + user.getPassword());
                System.out.println("possible_users info: " + u.getName() + "\nPassword: " + u.getPassword());
                if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                    model.addAttribute("name", u.getName());
                    return "login_success";
                }
            }
        }

        return "login_fail";
    }

}
