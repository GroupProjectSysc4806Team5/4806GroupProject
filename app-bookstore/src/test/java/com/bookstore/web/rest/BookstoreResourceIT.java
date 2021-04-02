package com.bookstore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bookstore.IntegrationTest;
import com.bookstore.domain.Bookstore;
import com.bookstore.repository.BookstoreRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BookstoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookstoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bookstores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookstoreMockMvc;

    private Bookstore bookstore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookstore createEntity(EntityManager em) {
        Bookstore bookstore = new Bookstore().name(DEFAULT_NAME);
        return bookstore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookstore createUpdatedEntity(EntityManager em) {
        Bookstore bookstore = new Bookstore().name(UPDATED_NAME);
        return bookstore;
    }

    @BeforeEach
    public void initTest() {
        bookstore = createEntity(em);
    }

    @Test
    @Transactional
    void createBookstore() throws Exception {
        int databaseSizeBeforeCreate = bookstoreRepository.findAll().size();
        // Create the Bookstore
        restBookstoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isCreated());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeCreate + 1);
        Bookstore testBookstore = bookstoreList.get(bookstoreList.size() - 1);
        assertThat(testBookstore.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createBookstoreWithExistingId() throws Exception {
        // Create the Bookstore with an existing ID
        bookstore.setId(1L);

        int databaseSizeBeforeCreate = bookstoreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookstoreMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookstores() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        // Get all the bookstoreList
        restBookstoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookstore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getBookstore() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        // Get the bookstore
        restBookstoreMockMvc
            .perform(get(ENTITY_API_URL_ID, bookstore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookstore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBookstore() throws Exception {
        // Get the bookstore
        restBookstoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookstore() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();

        // Update the bookstore
        Bookstore updatedBookstore = bookstoreRepository.findById(bookstore.getId()).get();
        // Disconnect from session so that the updates on updatedBookstore are not directly saved in db
        em.detach(updatedBookstore);
        updatedBookstore.name(UPDATED_NAME);

        restBookstoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBookstore.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBookstore))
            )
            .andExpect(status().isOk());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
        Bookstore testBookstore = bookstoreList.get(bookstoreList.size() - 1);
        assertThat(testBookstore.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookstore.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookstoreWithPatch() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();

        // Update the bookstore using partial update
        Bookstore partialUpdatedBookstore = new Bookstore();
        partialUpdatedBookstore.setId(bookstore.getId());

        partialUpdatedBookstore.name(UPDATED_NAME);

        restBookstoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookstore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookstore))
            )
            .andExpect(status().isOk());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
        Bookstore testBookstore = bookstoreList.get(bookstoreList.size() - 1);
        assertThat(testBookstore.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBookstoreWithPatch() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();

        // Update the bookstore using partial update
        Bookstore partialUpdatedBookstore = new Bookstore();
        partialUpdatedBookstore.setId(bookstore.getId());

        partialUpdatedBookstore.name(UPDATED_NAME);

        restBookstoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookstore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookstore))
            )
            .andExpect(status().isOk());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
        Bookstore testBookstore = bookstoreList.get(bookstoreList.size() - 1);
        assertThat(testBookstore.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookstore.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookstore() throws Exception {
        int databaseSizeBeforeUpdate = bookstoreRepository.findAll().size();
        bookstore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookstoreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookstore))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookstore in the database
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookstore() throws Exception {
        // Initialize the database
        bookstoreRepository.saveAndFlush(bookstore);

        int databaseSizeBeforeDelete = bookstoreRepository.findAll().size();

        // Delete the bookstore
        restBookstoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookstore.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookstore> bookstoreList = bookstoreRepository.findAll();
        assertThat(bookstoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
