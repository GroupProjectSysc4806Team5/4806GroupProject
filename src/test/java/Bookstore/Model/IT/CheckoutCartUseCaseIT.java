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

    @Test
    void testCartCheckout() throws Exception {
        mockMvc.perform(post("/login")
                .param("name", "User Test")
                .param("password", "userTest"))
                .andExpect(status().isOk());

        List<Book> bookList = (List<Book>) bookRepository.findAll();

        Assertions.assertTrue(bookList.size() > 0);
        Assertions.assertEquals(bookList.size(), 3);

        for (Book book: bookList) {
            mockMvc.perform(post("/user/add_to_cart")
                    .param("id", "" + book.getId()));
        }

        List<Cart> carts = (List <Cart>) cartRepository.findAll();
        Cart cart = carts.get(0);
        List<Book> addedBooks = cart.getBooks();
        Assertions.assertEquals(addedBooks.size(), 3);

        mockMvc.perform(get("/user/checkout_cart")).andExpect(status().isOk());
        mockMvc.perform(get("/user/complete_checkout")).andExpect(status().isOk());

        List<Sale> completedSales = (List<Sale>) saleRepository.findAll();
        Assertions.assertEquals(completedSales.size(), 1);

        List<Cart> cartsList = (List <Cart>) cartRepository.findAll();
        Cart nowEmptyCart = cartsList.get(0);
        Assertions.assertEquals(nowEmptyCart.getBooks().size(), 0);
    }
}
