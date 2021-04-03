package com.bookstore.web.rest;

import com.bookstore.repository.BookstoreRepository;
import com.bookstore.service.BookstoreService;
import com.bookstore.service.dto.BookstoreDTO;
import com.bookstore.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bookstore.domain.Bookstore}.
 */
@RestController
@RequestMapping("/api")
public class BookstoreResource {

    private final Logger log = LoggerFactory.getLogger(BookstoreResource.class);

    private static final String ENTITY_NAME = "bookstore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookstoreService bookstoreService;

    private final BookstoreRepository bookstoreRepository;

    public BookstoreResource(BookstoreService bookstoreService, BookstoreRepository bookstoreRepository) {
        this.bookstoreService = bookstoreService;
        this.bookstoreRepository = bookstoreRepository;
    }

    /**
     * {@code POST  /bookstores} : Create a new bookstore.
     *
     * @param bookstoreDTO the bookstoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookstoreDTO, or with status {@code 400 (Bad Request)} if the bookstore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bookstores")
    public ResponseEntity<BookstoreDTO> createBookstore(@RequestBody BookstoreDTO bookstoreDTO) throws URISyntaxException {
        log.debug("REST request to save Bookstore : {}", bookstoreDTO);
        if (bookstoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookstore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookstoreDTO result = bookstoreService.save(bookstoreDTO);
        return ResponseEntity
            .created(new URI("/api/bookstores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookstores/:id} : Updates an existing bookstore.
     *
     * @param id the id of the bookstoreDTO to save.
     * @param bookstoreDTO the bookstoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookstoreDTO,
     * or with status {@code 400 (Bad Request)} if the bookstoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookstoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookstores/{id}")
    public ResponseEntity<BookstoreDTO> updateBookstore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookstoreDTO bookstoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Bookstore : {}, {}", id, bookstoreDTO);
        if (bookstoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookstoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookstoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookstoreDTO result = bookstoreService.save(bookstoreDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookstoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bookstores/:id} : Partial updates given fields of an existing bookstore, field will ignore if it is null
     *
     * @param id the id of the bookstoreDTO to save.
     * @param bookstoreDTO the bookstoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookstoreDTO,
     * or with status {@code 400 (Bad Request)} if the bookstoreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookstoreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookstoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bookstores/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BookstoreDTO> partialUpdateBookstore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BookstoreDTO bookstoreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bookstore partially : {}, {}", id, bookstoreDTO);
        if (bookstoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookstoreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookstoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookstoreDTO> result = bookstoreService.partialUpdate(bookstoreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookstoreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bookstores} : get all the bookstores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookstores in body.
     */
    @GetMapping("/bookstores")
    public List<BookstoreDTO> getAllBookstores() {
        log.debug("REST request to get all Bookstores");
        return bookstoreService.findAll();
    }

    /**
     * {@code GET  /bookstores/:id} : get the "id" bookstore.
     *
     * @param id the id of the bookstoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookstoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookstores/{id}")
    public ResponseEntity<BookstoreDTO> getBookstore(@PathVariable Long id) {
        log.debug("REST request to get Bookstore : {}", id);
        Optional<BookstoreDTO> bookstoreDTO = bookstoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookstoreDTO);
    }

    /**
     * {@code DELETE  /bookstores/:id} : delete the "id" bookstore.
     *
     * @param id the id of the bookstoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookstores/{id}")
    public ResponseEntity<Void> deleteBookstore(@PathVariable Long id) {
        log.debug("REST request to delete Bookstore : {}", id);
        bookstoreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
