package Bookstore.Controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.Model.Cart;
import Bookstore.Model.Owner;
import Bookstore.Model.Sale;
import Bookstore.Model.User;
import Bookstore.repositories.BookRepository;
import Bookstore.repositories.BookstoreRepository;
import Bookstore.repositories.CartRepository;
import Bookstore.repositories.OwnerRepository;
import Bookstore.repositories.SaleRepository;
import Bookstore.repositories.UserRepository;

import org.springframework.web.bind.annotation.RequestBody;


/**
 * Controller with endpoints used to create and access entities for the bookstore system
 */
@RestController
public class BookstoreRestController {

    /**
     * Repositories for each entity, used to store all entities in the system
     */
//    @Autowired
//    private BookRepository bookRepository;
//    @Autowired
//    private BookstoreRepository bookstoreRepository;
//    @Autowired
//    private OwnerRepository bookstoreOwnerRepository;
//    @Autowired
//    private UserRepository customerRepository;
//    @Autowired
//    private CartRepository shoppingCartRepository;
//    @Autowired
//    private SaleRepository saleRepository;
//
//
//    //Owner REST endpoints
//    /**
//     * Retrieve Owner with given ID
//     * @param bookstoreOwnerId ID of Owner to be retrieved
//     * @return Owner found in repository from given ID
//     */
//    @GetMapping("/api/getBookstoreOwner")
//    public Owner getBookstoreOwner(@RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
//        return bookstoreOwnerRepository.findById(bookstoreOwnerId);
//    }
//
//    /**
//     * Retrieve all Owner entities in the system
//     * @return List of all Owner entities in the system
//     */
//    @GetMapping("/api/getBookstoreOwners")
//    public Iterable<Owner> getBookstoreOwner() {
//        return bookstoreOwnerRepository.findAll();
//    }
//
//    /**
//     * Create new Owner with given name
//     * @param bookstoreOwnerName Name for new Owner
//     * @param bookstoreOwnerUsername Username for new Owner
//     * @param bookstoreOwnerPassword Password for new Owner
//     * @return Newly created Owner
//     */
//    @PostMapping("/api/newBookstoreOwner")
//    public Owner newBookstoreOwner(@RequestParam(value = "bookstoreOwnerName") String bookstoreOwnerName, @RequestParam(value = "bookstoreOwnerUsername") String bookstoreOwnerUsername, @RequestParam(value = "bookstoreOwnerPassword") String bookstoreOwnerPassword) {
//        Owner bookstoreOwner = new Owner(bookstoreOwnerUsername, bookstoreOwnerPassword, bookstoreOwnerName);
//        bookstoreOwnerRepository.save(bookstoreOwner);
//        return bookstoreOwner;
//    }
//
//
//    //Bookstore REST endpoints
//    /**
//     * Retrieve all Bookstore entities in the system.
//     * @return List of Bookstores in the system
//     */
//    @GetMapping("/api/getBookstores")
//    public Iterable<Bookstore> getBookstores() {
//        return bookstoreRepository.findAll();
//    }
//
//    /**
//     * Retrieve Bookstore entities that are owned by given Owner
//     * @param bookstoreOwnerId ID of Owner
//     * @return List of Bookstores that are owned by given Owner
//     */
//    @GetMapping("/api/getBookstoresByBookstoreOwner")
//    public Iterable<Bookstore> getBookstoreByBookstoreOwner(@RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
//        Owner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
//        return bookstoreOwner.getBookstores();
//    }
//
//    /**
//     * Create new Bookstore with given name and owned by given Owner
//     * @param bookstoreName Name for new Bookstore
//     * @param bookstoreOwnerId ID of Owner to be set as owner of new Bookstore
//     * @return Newly created Bookstore
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/api/newBookstore")
//    public Bookstore newBookstore(@RequestParam(value = "bookstoreName") String bookstoreName, @RequestParam(value = "bookstoreOwnerId") long bookstoreOwnerId) {
//        Owner bookstoreOwner = bookstoreOwnerRepository.findById(bookstoreOwnerId);
//        if (bookstoreOwner == null)
//            return null;
//        Bookstore bookstore = new Bookstore(bookstoreName);
//        bookstore.setOwner(bookstoreOwner);
//        bookstoreOwner.addBookstore(bookstore);
//        bookstoreOwnerRepository.save(bookstoreOwner);
//        return bookstore;
//    }
//
//
//    //Book REST endpoints
//    /**
//     * Retrieve Book with given ID
//     * @param bookId ID of Book to retrieve
//     * @return Retrieved Book with given ID bookId
//     */
//    @GetMapping("/api/getBook")
//    public Book getBook(@RequestParam(value = "bookId") long bookId) {
//        return bookRepository.findById(bookId);
//    }
//
//    /**
//     * Retrieve all Book entities in the system
//     * @return List of all Book entities in the system
//     */
//    @GetMapping("/api/getBooks")
//    public Iterable<Book> getBooks() {
//        return bookRepository.findAll();
//    }
//
//    /**
//     * Retrieve Book entities that are in Bookstore with given ID
//     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
//     * @return List of Book entities found in Bookstore with given ID
//     */
//    @GetMapping("/api/getBooksByBookstore")
//    public Iterable<Book> getBooksByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
//        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
//        if (bookstore == null)
//            return null;
//        return bookRepository.findByBookstore(bookstore);
//    }
//
//    /**
//     * Retrieve available Book entities that are in Bookstore with given ID
//     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
//     * @return List of available Book entities found in Bookstore with given ID
//     */
//    @GetMapping("/api/getBooksAvailableByBookstore")
//    public Iterable<Book> getBooksAvailableByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
//        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
//        if (bookstore == null)
//            return null;
//        ArrayList<Book> books = new ArrayList<>();
//        for (Book book : bookstore.getBooks()){
//            if (book.getAvailable())
//                books.add(book);
//        }
//        if (books.size() == 0)
//            return null;
//        return books;
//    }
//
//    /**
//     * Retrieve sold Book entities that are in Bookstore with given ID bookstoreOwnerId
//     * @param bookstoreId ID of Bookstore that will be searched for all Book entities
//     * @return List of sold Book entities found in Bookstore with given ID bookstoreOwnerId
//     */
//    @GetMapping("/api/getBooksSoldByBookstore")
//    public Iterable<Book> getBooksSoldByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
//        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
//        if (bookstore == null)
//            return null;
//        ArrayList<Book> books = new ArrayList<>();
//        for (Book book : bookstore.getBooks()){
//            if (!book.getAvailable())
//                books.add(book);
//        }
//        if (books.size() == 0)
//            return null;
//        return books;
//    }
//
//    /**
//     * Create new Book with given parameters
//     * @param bookName Name of new Book
//     * @param isbn ISBN of new Book
//     * @param picture Picture of new Book
//     * @param description Description of new Book
//     * @param author Author of new Book
//     * @param publisher Publisher of new Book
//     * @param bookstoreId ID of Bookstore that new Book will be located in
//     * @return Newly created Book
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/api/newBook")
//    public Book newBook(@RequestParam(value = "bookName") String bookName, @RequestParam(value = "isbn") String isbn, @RequestParam(value = "picture") String picture, @RequestParam(value = "description") String description, @RequestParam(value = "author") String author, @RequestParam(value = "publisher") String publisher, @RequestParam(value = "bookstoreId") long bookstoreId) {
//        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
//        if (bookstore == null)
//            return null;
//        Book book = new Book(bookName, isbn, picture, description, author, publisher);
//        bookstore.addBook(book);
//        book.setBookstore(bookstore);
//        bookstoreRepository.save(bookstore);
//        return book;
//    }
//
//
//    //User REST endpoints
//    /**
//     * Retrieve customer with given ID
//     * @param customerId ID of customer to retrieve
//     * @return Retrieved User from given ID
//     */
//    @GetMapping("/api/getCustomer")
//    public User getCustomer(@RequestParam(value = "customerId") long customerId) {
//        return customerRepository.findById(customerId);
//    }
//
//    /**
//     * Retrieve all User entities in the system
//     * @return List of User entities in the system
//     */
//    @GetMapping("/api/getCustomers")
//    public Iterable<User> getCustomers() {
//        return customerRepository.findAll();
//    }
//
//    /**
//     * Create new User with given parameters
//     * @param customerName Name of new User
//     * @param address Address of new User
//     * @param email Email of new User
//     * @param phoneNumber Phone Number of new User
//     * @param username Username for new User
//     * @param password Password for new User
//     * @return Newly created User
//     */
//    @PostMapping("/api/newCustomer")
//    public User newCustomer(@RequestParam(value = "customerName") String customerName, @RequestParam(value = "address") String address, @RequestParam(value = "email") String email, @RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
//        User customer = new User(username, password, customerName, address, email, phoneNumber);
//        Cart shoppingCart = new Cart();
//        shoppingCart.setCustomer(customer);
//        customer.setShoppingCart(shoppingCart);
//        customerRepository.save(customer);
//        return customer;
//    }
//
//
//    //Shopping Cart REST endpoints
//    /**
//     * Retrieve ShoppingCart linked to given User
//     * @param customerId ID of User used to find linked ShoppingCart
//     * @return ShoppingCart linked to given User
//     */
//    @GetMapping("/api/getShoppingCartByCustomer")
//    public Cart getShoppingCartByCustomer(@RequestParam(value = "customerId") long customerId) {
//        User customer = customerRepository.findById(customerId);
//        if (customer == null)
//            return null;
//        return shoppingCartRepository.findByCustomer(customer);
//    }
//
//    /**
//     * Add given Book to ShoppingCart linked to given User
//     * @param customerId ID of User used to find linked ShoppingCart
//     * @param bookId ID of Book to add to ShoppingCart
//     * @return ShoppingCart of given User, containing given Book
//     */
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/api/addBookToCustomersShoppingCart")
//    public Cart addBookToShoppingCart(@RequestParam(value = "customerId") long customerId , @RequestParam(value = "bookId") long bookId) {
//        User customer = customerRepository.findById(customerId);
//        if (customer == null)
//            return null;
//        Cart shoppingCart = customer.getShoppingCart();
//        Book book = bookRepository.findById(bookId);
//        if (book == null)
//            return null;
//        shoppingCart.addBook(book);
//        book.addShoppingCart(shoppingCart);
//        shoppingCartRepository.save(shoppingCart);
//        return shoppingCart;
//    }
//
//
//    //Sale REST endpoints
//    /**
//     * Retrieve all Sale entities linked to given User
//     * @param customerId ID of User
//     * @return List of Sale entities linked to given User
//     */
//    @GetMapping("/api/getSalesByCustomer")
//    public Iterable<Sale> getSalesByCustomer(@RequestParam(value = "customerId") long customerId) {
//        User customer = customerRepository.findById(customerId);
//        if (customer == null)
//            return null;
//        return saleRepository.findByCustomer(customer);
//    }
//
//    /**
//     * Retrieve all Sale entities linked to given Bookstore
//     * @param bookstoreId ID of Bookstore
//     * @return List of Sale entities linked to given Bookstore
//     */
//    @GetMapping("/api/getSalesByBookstore")
//    public Iterable<Sale> getSalesByBookstore(@RequestParam(value = "bookstoreId") long bookstoreId) {
//        Bookstore bookstore = bookstoreRepository.findById(bookstoreId);
//        if (bookstore == null)
//            return null;
//        return bookstore.getSales();
//    }
//
//    /**
//     * Create new Sale linked to given User
//     * @param customerId ID of User
//     * @return Newly create Sale linked to given User
//     */
//    @PreAuthorize("hasRole('USER')")
//    @PostMapping("/api/newSale")
//    public Sale newSale(@RequestParam(value = "customerId") long customerId) {
//        User customer = customerRepository.findById(customerId);
//        if (customer == null)
//            return null;
//        Cart shoppingCart = customer.getShoppingCart();
//        Sale sale = shoppingCart.checkout();
//        saleRepository.save(sale);
//        return sale;
//    }

    // /**
    //  * Endpoint for logging in, doesn't do anything significant other than start a session
    //  */
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    // @GetMapping("/login")
    // public String login(){
    //     return "successfully authenticated";
    // }
    
}