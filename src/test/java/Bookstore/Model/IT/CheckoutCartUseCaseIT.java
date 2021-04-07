package Bookstore.Model.IT;

import Bookstore.Model.Book;
import Bookstore.Model.Cart;
import Bookstore.Model.Sale;
import Bookstore.repositories.BookRepository;
import Bookstore.repositories.CartRepository;
import Bookstore.repositories.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutCartUseCaseIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private BookRepository bookRepository;

    private List<Book> bookList;
    private Cart cart;
    private Integer quantity1;
    private Integer quantity2;
    private Integer quantity3;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/login-check")
            .param("name", "User Test")
            .param("password", "userTest"))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testCartCheckout() throws Exception {
        bookList = bookRepository.findAll();

        Assertions.assertTrue(bookList.size() > 0);
        Assertions.assertEquals(bookList.size(), 9);

        for (Book book: bookList) {
            mockMvc.perform(post("/user/add_to_cart")
                    .param("id", "" + book.getId()));
        }

        cart = cartRepository.findAll().stream().findFirst().orElse(null);
        List<Book> addedBooks = cart.getBooks();
        Assertions.assertEquals(addedBooks.size(), 9);

        // Get initial quantities of books
        quantity1 = bookList.get(0).getQuantity();
        quantity2 = bookList.get(1).getQuantity();
        quantity3 = bookList.get(2).getQuantity();

        // Attempt to increase quantity of each book in cart (quantities should be 1, 2, 2 after)
        for (Book book: bookList) {
            mockMvc.perform(post("/user/cart_increase_book_quantity")
                    .param("id", "" + book.getId()));
        }

        // Test that quantities are updated
        cart = cartRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertEquals(cart.getQuantity(bookList.get(0).getId()), 1);
        Assertions.assertEquals(cart.getQuantity(bookList.get(1).getId()), 2);
        Assertions.assertEquals(cart.getQuantity(bookList.get(2).getId()), 2);

        mockMvc.perform(post("/user/cart_decrease_book_quantity")
                .param("id", "" + bookList.get(2).getId()));

        // Test that we decreased the quantity of book 3 in the cart
        cart = cartRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertEquals(cart.getQuantity(bookList.get(2).getId()), 1);

        mockMvc.perform(get("/user/checkout_cart")).andExpect(status().isOk());
        mockMvc.perform(get("/user/complete_checkout")).andExpect(status().isOk());

        // Fetch updated books
        List<Book> bookList = (List<Book>) bookRepository.findAll();

        //Test the decrement
        // Book 1 initially has only 1 book so quantity can't be below 0
        Assertions.assertEquals(bookList.get(0).getQuantity(), quantity1 - 1);
        Assertions.assertEquals(bookList.get(1).getQuantity(), quantity2 - 2);
        Assertions.assertEquals(bookList.get(2).getQuantity(), quantity3 - 1);

        // Test a sale has been added to the repo
        List<Sale> completedSales = saleRepository.findAll();
        Assertions.assertEquals(completedSales.size(), 1);

        // Test that the cart is now empty
        cart = cartRepository.findAll().stream().findFirst().orElse(null);
        Assertions.assertEquals(cart.getBooks().size(), 0);
    }
}
