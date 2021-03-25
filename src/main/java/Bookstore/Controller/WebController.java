package Bookstore.Controller;

import Bookstore.Model.*;
import Bookstore.repositories.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@Controller
public class WebController {

    private BookRepository bookRepo;
    private BookstoreRepository bookstoreRepo;
    private CartRepository cartRepo;
    private UserRepository userRepo;
    private OwnerRepository ownerRepo;
    private User currentUser;

    public WebController(BookRepository bookRepo, BookstoreRepository bookstoreRepo, CartRepository cartRepo, UserRepository userRepo, OwnerRepository ownerRepo) {
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
    public String newOwner(Model model) {

        model.addAttribute(new Owner());
        return "new_owner";
    }

    @PostMapping("/new-owner-successful")
    public String newOwnerSuccess(Model model, @ModelAttribute Owner owner) {
        ownerRepo.save(owner);
        return "new_owner_successful";
    }

    @GetMapping("/new-book")
    public String newBook(Model model) {
        model.addAttribute(new Book());
        return "new_book";

    }

    @PostMapping("/new-book-successful")
    public String newBookSuccess(Model model, @ModelAttribute Book book) {
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
    public String newUser(Model model) {
        model.addAttribute(new User());
        return "new_user";
    }

    @PostMapping("new-user-successful")
    public String newUserSuccess(Model model, @ModelAttribute User user) {
        userRepo.save(user);
        return "new_user_successful";
    }

    @GetMapping("add-book")
    public String addBook(Model model, @RequestParam(required = false, name = "id") String id) {

        User u = userRepo.findById(Long.parseLong(id));

        if (u == null) {
            return "add_book_error";
        }

        Iterable<Book> books = bookRepo.findAll();
        ArrayList<Book> book_list = new ArrayList<>();
        Iterator i = books.iterator();

        while (i.hasNext()) {
            Book b = (Book) i.next();
            book_list.add(b);
        }

        // The book_id of the book to be added to a User's Cart
        String book_id = "";

        model.addAttribute("user", u);
        model.addAttribute("list", book_list);
        model.addAttribute("id", id);
        model.addAttribute("book_id", book_id);
        return "add_book";

    }

    @PostMapping("add-book-successful")
    public String addBookSuccess(Model model, @RequestParam(required = false, name = "id") String id, @ModelAttribute String book_id, @ModelAttribute User user) {
        Book b = bookRepo.findById(Long.parseLong(book_id));
        if (b == null) {
            return "error";
        }

        user.cart.addBook(b);


        model.addAttribute("cart", user.getCart());

        return "add_book_successful";

    }

    // Temporary Mapping, this will replace the main page with a login
    @GetMapping("login")
    public String ownerLogin(Model model) {

        model.addAttribute("user", new User());
        return "login";
    }

    // Temporary Post Mapping
    @PostMapping("login")
    public String checkLogin(Model model, @ModelAttribute User user) {

        List<Owner> possible_owners = ownerRepo.findByName(user.getName());
        List<User> possible_users = userRepo.findByName(user.getName());

        // Check the owners if the list is not empty
        if (!possible_owners.isEmpty()) {
            for (Owner o : possible_owners) {
                System.out.println("========================================");
                System.out.println("Provided info: " + user.getName() + "\nPassword: " + user.getPassword());
                System.out.println("possible_owners info: " + o.getName() + "\nPassword: " + o.getPassword());

                if (o.getName().equals(user.getName()) && o.getPassword().equals(user.getPassword())) {
                    model.addAttribute("name", o.getName());
                    model.addAttribute("id", o.getId());
                    return "owner/login_success";
                }
            }

            return "login_fail";
        } else if (!possible_users.isEmpty()) {
            for (User u : possible_users) {
                System.out.println("========================================");
                System.out.println("Provided info: " + user.getName() + "\nPassword: " + user.getPassword());
                System.out.println("possible_users info: " + u.getName() + "\nPassword: " + u.getPassword());
                if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                    model.addAttribute("name", u.getName());
                    model.addAttribute("id", u.getId());
                    currentUser = u;
                    return "user/login_success";
                }
            }
        }

        return "login_fail";
    }

    /*
     * This function will take a owner input to create a bookstore
     */
    @GetMapping("owner_open_bookstore")
    public String ownerOpenBookstore(Model model, @RequestParam(name = "id") String id) {
        model.addAttribute("store", new Bookstore());
        model.addAttribute("id", id);

        return "owner/open_bookstore";
    }

    @PostMapping("owner_opened_bookstore")
    public String openedBookstore(Model model, @ModelAttribute Bookstore store, @RequestParam(name = "id") String id) {
        Owner owner = ownerRepo.findById(Long.parseLong(id));


        //bookstoreRepo.save(store);
        store.setOwner(owner);
        owner.addStore(store);
        ownerRepo.save(owner);

        model.addAttribute("store_name", store.getName());
        model.addAttribute("id", id);
        return "owner/opened_bookstore";
    }

    /*
     * This function will allow a owner to view a list of their bookstores
     */
    @GetMapping("view_bookstores")
    public String viewBookstores(Model model, @RequestParam(name = "id") String id) {
        Owner owner = ownerRepo.findById(Long.parseLong(id));
        List<Bookstore> stores = owner.getStores();
        model.addAttribute("id", id);

        if (!stores.isEmpty()) {
            model.addAttribute("stores", stores);

            return "owner/bookstores";
        } else {
            return "owner/no_bookstores";
        }
    }

    /*
     * Create a login page for the logged in user
     */
    @GetMapping("login_page_owner")
    public String loginPageOwner(Model model, @RequestParam(name = "id") String id) {
        Owner owner = ownerRepo.findById(Long.parseLong(id));
        model.addAttribute("id", id);
        model.addAttribute("name", owner.getName());
        return "owner/login_success";
    }

    /*
     * Create a page to view a bookstore and add books to it
     */
    @GetMapping("bookstore")
    public String viewBookstoreOwner(Model model, @RequestParam(name = "id") String id) {

        // Get the bookstore
        Bookstore store = bookstoreRepo.findById(Long.parseLong(id));

        model.addAttribute("name", store.getName());
        model.addAttribute("owner", store.getOwner());
        model.addAttribute("store_id", store.getId());
        model.addAttribute("id", store.getOwner().getId());


        return "owner/view-bookstore";
    }

    @GetMapping("view_books")
    public String viewBook(Model model, @RequestParam(name = "id") String id) {
        Bookstore store = bookstoreRepo.findById(Long.parseLong(id));
        List<Book> books = bookRepo.findByStore(store);

        model.addAttribute("id", id);
        model.addAttribute("books", books);

        return "owner/view_books";
    }

    @GetMapping("owner_add_book")
    public String ownerAddBook(Model model, @RequestParam(name = "id") String id) {
        model.addAttribute("book", new Book());
        model.addAttribute("id", id);
        return "owner/add_book";
    }

    @PostMapping("added_book")
    public String addedBook(Model model, @RequestParam(name = "id") String id, @ModelAttribute Book book) {
        model.addAttribute("name", book.getBookName());
        //bookRepo.save(book);

        model.addAttribute("id", id);
        // Get the store for it's store id
        Bookstore store = bookstoreRepo.findById(Long.parseLong(id));
        book.setStore(store);
        store.addBook(book);
        bookstoreRepo.save(store);

        return "owner/added_book";
    }

    @GetMapping("edit_book")
    public String editBook(Model model, @RequestParam(name = "id") String id) {
        Book book = bookRepo.findById(Long.parseLong(id));

        model.addAttribute("book", book);

        //Attach the Store to the model
        model.addAttribute("store", book.getStore());

        return "owner/edit_book";
    }

    @PostMapping("edited_book")
    public String editedBook(Model model, @RequestParam(name = "id") String id, @ModelAttribute Book book, @ModelAttribute Bookstore store) {

        // use the book from the repo and add values using the
        Book repoBook = bookRepo.findById(Long.parseLong(id));
        model.addAttribute("id", repoBook.getStore().getId());

//        System.out.println("passed id:" + id);
//        System.out.println("store id:" + repoBook.getStore().getId());

        repoBook.setBookName(book.getBookName());
        repoBook.setISBN(book.getISBN());
        repoBook.setPicture(book.getPicture());
        repoBook.setAuthor(book.getAuthor());
        repoBook.setDescription(book.getDescription());
        repoBook.setPublisher(book.getPublisher());

        //bookstoreRepo.save(store);
        bookRepo.save(repoBook);

        return "owner/edited_book";
    }

    /**
     * User Mappings
     */

    private User getUser(long id) {
        User user = userRepo.findById(id);
        return user;
    }

    @GetMapping("user/view_all_bookstores")
    public String viewAllBookstores(Model model) {
        List<Bookstore> stores = (List<Bookstore>) bookstoreRepo.findAll();

        model.addAttribute("stores", stores);
        return "user/bookstores";
    }

    @GetMapping("user/view_bookstore")
    public String viewBookstore(Model model, @RequestParam(name = "id") String id) {
        Bookstore store = bookstoreRepo.findById(Long.parseLong(id));
        List<Book> books = store.getBooks();

        if (books == null) {
            books = new ArrayList<>();
        }

        model.addAttribute("store", store);
        model.addAttribute("books", books);
        return "user/view_bookstore";
    }

    @GetMapping("user/view_book")
    public String viewBook(
        Model model,
        @RequestParam(name = "id") String store_id,
        @RequestParam(name = "id") String book_id) {

        Book book = bookRepo.findById(Long.parseLong(book_id));
        model.addAttribute("store_id", store_id);
        model.addAttribute("book", book);
        return "user/view_book";
    }

    @GetMapping("user/view_cart")
    public String viewCart(Model model) {
        User user = getUser(currentUser.getId());
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart(user);

            user.setCart(cart);
        }

        List<Book> books = cart.getBooks();

        if (books == null) {
            books = new ArrayList<>();
        }
        model.addAttribute("user", user);
        model.addAttribute("books", books);

        return "user/view_cart";
    }

    @PostMapping("user/add_to_cart")
    public String addToCart(
        @RequestParam(name = "id") String book_id,
        HttpServletRequest request) {

        User user = getUser(currentUser.getId());
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart(user);
            user.setCart(cart);
        }

        Book book = bookRepo.findById(Long.parseLong(book_id));
        if (cart.getBooks().stream().noneMatch(_book -> book.getId().equals(_book.getId()))) {
            cart.addBook(book);
            user.setCart(cart);
            cartRepo.save(cart);
        }

        // After adding the book to the cart, redirect back to the book page
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("user/remove_from_cart")
    public String removeFromCart(
        @RequestParam(name = "id") String id,
        HttpServletRequest request) {

        User user = getUser(currentUser.getId());
        Cart cart = user.getCart();

        if (cart != null) {
            Book book = bookRepo.findById(Long.parseLong(id));
            Predicate<Book> isMatchingBookId = _book -> book.getId().equals(_book.getId());
            if (cart.getBooks().stream().anyMatch(isMatchingBookId)) {
                List<Book> books = cart.getBooks();
                Book bookToRemove = books.stream().filter(isMatchingBookId).findFirst().get();

                books.remove(bookToRemove);
                cart.setBooks(books);
                cartRepo.save(cart);
            }
        }

        return "redirect:" + request.getHeader("Referer");
    }
}
