package com.bookstore.service;

import com.bookstore.domain.Bookstore;
import com.bookstore.repository.BookstoreRepository;
import com.bookstore.service.dto.BookstoreDTO;
import com.bookstore.service.mapper.BookstoreMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bookstore}.
 */
@Service
@Transactional
public class BookstoreService {

    private final Logger log = LoggerFactory.getLogger(BookstoreService.class);

    private final BookstoreRepository bookstoreRepository;

    private final BookstoreMapper bookstoreMapper;

    public BookstoreService(BookstoreRepository bookstoreRepository, BookstoreMapper bookstoreMapper) {
        this.bookstoreRepository = bookstoreRepository;
        this.bookstoreMapper = bookstoreMapper;
    }

    /**
     * Save a bookstore.
     *
     * @param bookstoreDTO the entity to save.
     * @return the persisted entity.
     */
    public BookstoreDTO save(BookstoreDTO bookstoreDTO) {
        log.debug("Request to save Bookstore : {}", bookstoreDTO);
        Bookstore bookstore = bookstoreMapper.toEntity(bookstoreDTO);
        bookstore = bookstoreRepository.save(bookstore);
        return bookstoreMapper.toDto(bookstore);
    }

    /**
     * Partially update a bookstore.
     *
     * @param bookstoreDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BookstoreDTO> partialUpdate(BookstoreDTO bookstoreDTO) {
        log.debug("Request to partially update Bookstore : {}", bookstoreDTO);

        return bookstoreRepository
            .findById(bookstoreDTO.getId())
            .map(
                existingBookstore -> {
                    bookstoreMapper.partialUpdate(existingBookstore, bookstoreDTO);
                    return existingBookstore;
                }
            )
            .map(bookstoreRepository::save)
            .map(bookstoreMapper::toDto);
    }

    /**
     * Get all the bookstores.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BookstoreDTO> findAll() {
        log.debug("Request to get all Bookstores");
        return bookstoreRepository.findAll().stream().map(bookstoreMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bookstore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookstoreDTO> findOne(Long id) {
        log.debug("Request to get Bookstore : {}", id);
        return bookstoreRepository.findById(id).map(bookstoreMapper::toDto);
    }

    /**
     * Delete the bookstore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bookstore : {}", id);
        bookstoreRepository.deleteById(id);
    }
}
