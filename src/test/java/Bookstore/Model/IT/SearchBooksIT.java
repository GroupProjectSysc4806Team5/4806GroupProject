package Bookstore.Model.IT;

import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.repositories.BookRepository;

import Bookstore.repositories.BookstoreRepository;
import Bookstore.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchBooksIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookstoreRepository bookstoreRepos;

    @Test
    public void testBookSearch() throws Exception {
        mockMvc.perform(post("/login")
                .param("name", "User Test")
                .param("password", "userTest"))
                .andExpect(status().isOk());

        // Get the Book to be searched for
        List<Book> books = bookRepo.findByBookName("Test Book");

        // Assert that there is only one book returned
        assert(books.size() == 1);

        // This is the correct book that the search should return
        Book book = books.get(0);

        // Assert that the correct book is returned
        assert(book.getBookName().equals("Test Book"));

        // Go to the search page
        mockMvc.perform(get("/user/search_bookstores")).andExpect(status().isOk());

        // Perform a search for "Test Book" and return a result of the model
        MvcResult result =  mockMvc.perform(post("/user/searched").param("query","Test Book")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

        // From the model the list of Books on the model
        List<Book> books_model = (List<Book>) result.getModelAndView().getModel().get("books");

        // Assert that there is only one book and it is the same book as the one found from before
        assert(book.equals(books_model.get(0)));
    }
}
