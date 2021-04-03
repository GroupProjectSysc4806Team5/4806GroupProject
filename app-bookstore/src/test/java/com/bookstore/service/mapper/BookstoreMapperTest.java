package com.bookstore.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookstoreMapperTest {

    private BookstoreMapper bookstoreMapper;

    @BeforeEach
    public void setUp() {
        bookstoreMapper = new BookstoreMapperImpl();
    }
}
